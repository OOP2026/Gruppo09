package implementazioneDao;

import dao.MedicoDAO;
import database_connection.ConnessioneDatabase;
import model.Medico;
import model.Reparto;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MedicoPostgresDAO implements MedicoDAO {
    private Connection conn;

    public MedicoPostgresDAO() {
        // Recupera la connessione centralizzata dal Singleton
        this.conn = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public List<Medico> getAllMedici() {
        List<Medico> lista = new ArrayList<>();
        String query = "SELECT m.matricola, m.username, m.idreparto, r.nome AS nome_reparto " +
                "FROM medico m LEFT JOIN reparto r ON m.idreparto = r.idreparto ORDER BY m.matricola ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Reparto rep = new Reparto(rs.getInt("idreparto"), rs.getString("nome_reparto"));
                lista.add(new Medico(rs.getString("username"), "", rs.getString("matricola"), rep));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Medico> getSostitutiIdonei(String matricolaAssente, LocalDate inizio, LocalDate fine) {
        List<Medico> sostituti = new ArrayList<>();

        // 1. Estrae i medici appartenenti allo stesso reparto del medico assente
        List<Medico> candidatiReparto = new ArrayList<>();
        String queryMedici = "SELECT m.matricola, m.username, m.idreparto, r.nome AS nome_reparto " +
                "FROM medico m " +
                "LEFT JOIN reparto r ON m.idreparto = r.idreparto " +
                "WHERE m.idreparto = (SELECT idreparto FROM medico WHERE matricola = ?) " +
                "AND m.matricola <> ?";

        try (PreparedStatement pstmt = conn.prepareStatement(queryMedici)) {
            pstmt.setString(1, matricolaAssente);
            pstmt.setString(2, matricolaAssente);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Reparto rep = new Reparto(rs.getInt("idreparto"), rs.getString("nome_reparto"));
                    candidatiReparto.add(new Medico(rs.getString("username"), "", rs.getString("matricola"), rep));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return sostituti;
        }

        // 2. Recupera i turni coperti dal medico assente per capire quali slot sostituire
        String queryTurniAssente = "SELECT giornosettimana, orainizio, orafine FROM turno WHERE codmedico = ?";

        // 3. Valuta la disponibilità di ciascun collega per tutto il periodo richiesto
        for (Medico candidato : candidatiReparto) {
            boolean idoneoPerTuttoIlPeriodo = true;
            LocalDate dataCorrente = inizio;

            while (!dataCorrente.isAfter(fine)) {
                String giornoSettimana = null;
                switch (dataCorrente.getDayOfWeek()) {
                    case MONDAY:    giornoSettimana = "Lunedì"; break;
                    case TUESDAY:   giornoSettimana = "Martedì"; break;
                    case WEDNESDAY: giornoSettimana = "Mercoledì"; break;
                    case THURSDAY:  giornoSettimana = "Giovedì"; break;
                    case FRIDAY:    giornoSettimana = "Venerdì"; break;
                    case SATURDAY:  giornoSettimana = "Sabato"; break;
                    case SUNDAY:    giornoSettimana = "Domenica"; break;
                }

                try (PreparedStatement pstmt = conn.prepareStatement(queryTurniAssente)) {
                    pstmt.setString(1, matricolaAssente);

                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            if (rs.getString("giornosettimana").equalsIgnoreCase(giornoSettimana)) {
                                LocalTime oraInizio = rs.getTime("orainizio").toLocalTime();
                                LocalTime oraFine = rs.getTime("orafine").toLocalTime();

                                // Invoca il metodo di controllo puntuale definito sotto
                                if (!verificaDisponibilita(candidato.getMatricola(), dataCorrente, oraInizio, oraFine)) {
                                    idoneoPerTuttoIlPeriodo = false;
                                    break;
                                }
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    idoneoPerTuttoIlPeriodo = false;
                }

                if (!idoneoPerTuttoIlPeriodo) break;
                dataCorrente = dataCorrente.plusDays(1);
            }

            if (idoneoPerTuttoIlPeriodo) {
                sostituti.add(candidato);
            }
        }
        return sostituti;
    }

    @Override
    public boolean verificaDisponibilita(String matricolaMedico, LocalDate data, LocalTime oraInizio, LocalTime oraFine) {
        // Traduzione della data nel giorno della settimana corrispondente
        String giornoSettimanaInItaliano = null;
        switch (data.getDayOfWeek()) {
            case MONDAY:    giornoSettimanaInItaliano = "Lunedì"; break;
            case TUESDAY:   giornoSettimanaInItaliano = "Martedì"; break;
            case WEDNESDAY: giornoSettimanaInItaliano = "Mercoledì"; break;
            case THURSDAY:  giornoSettimanaInItaliano = "Giovedì"; break;
            case FRIDAY:    giornoSettimanaInItaliano = "Venerdì"; break;
            case SATURDAY:  giornoSettimanaInItaliano = "Sabato"; break;
            case SUNDAY:    giornoSettimanaInItaliano = "Domenica"; break;
        }

        // Verifica che il medico sia effettivamente in servizio in quella fascia oraria
        String queryTurno = "SELECT COUNT(*) FROM turno WHERE codmedico = ? AND LOWER(giornosettimana) = LOWER(?) AND orainizio <= ? AND orafine >= ?";
        try (PreparedStatement pstmt = conn.prepareStatement(queryTurno)) {
            pstmt.setString(1, matricolaMedico);
            pstmt.setString(2, giornoSettimanaInItaliano);
            pstmt.setTime(3, java.sql.Time.valueOf(oraInizio));
            pstmt.setTime(4, java.sql.Time.valueOf(oraFine));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Controlla che non vi siano visite o prestazioni sovrapposte negli stessi orari
        String querySovrapposizione = "SELECT COUNT(*) FROM prestazione p " +
                "JOIN ricovero r ON p.idricovero = r.idricovero " +
                "WHERE p.codmedico = ? AND r.datainizio = ? " +
                "AND NOT (p.orafine <= ? OR p.orainizio >= ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(querySovrapposizione)) {
            pstmt.setString(1, matricolaMedico);
            pstmt.setDate(2, java.sql.Date.valueOf(data));
            pstmt.setTime(3, java.sql.Time.valueOf(oraInizio));
            pstmt.setTime(4, java.sql.Time.valueOf(oraFine));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Controlla che il medico non abbia registrato un periodo di malattia o assenza
        String queryAssenza = "SELECT COUNT(*) FROM assenza_medico WHERE codmedico = ? AND ? BETWEEN datainizio AND datafine";
        try (PreparedStatement pstmt = conn.prepareStatement(queryAssenza)) {
            pstmt.setString(1, matricolaMedico);
            pstmt.setDate(2, java.sql.Date.valueOf(data));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}