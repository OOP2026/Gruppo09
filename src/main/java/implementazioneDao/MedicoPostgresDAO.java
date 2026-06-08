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
        this.conn = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public List<Medico> getAllMedici() {
        List<Medico> lista = new ArrayList<>();
        String query = "SELECT m.matricola, m.username, m.idreparto, r.nome AS nome_reparto " +
                "FROM medico m LEFT JOIN reparto r ON m.idreparto = r.idreparto " +
                "ORDER BY m.matricola ASC";

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

        // Recupera i colleghi dello stesso reparto escludendo il medico assente
        List<Medico> candidati = new ArrayList<>();
        String queryMedici = "SELECT m.matricola, m.username, m.idreparto, r.nome AS nome_reparto " +
                "FROM medico m LEFT JOIN reparto r ON m.idreparto = r.idreparto " +
                "WHERE m.idreparto = (SELECT idreparto FROM medico WHERE matricola = ?) " +
                "AND m.matricola <> ?";

        try (PreparedStatement pstmt = conn.prepareStatement(queryMedici)) {
            pstmt.setString(1, matricolaAssente);
            pstmt.setString(2, matricolaAssente);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Reparto rep = new Reparto(rs.getInt("idreparto"), rs.getString("nome_reparto"));
                    candidati.add(new Medico(rs.getString("username"), "", rs.getString("matricola"), rep));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return sostituti;
        }

        // Per ogni candidato verifica la disponibilità giorno per giorno nel periodo
        for (Medico candidato : candidati) {
            boolean idoneo = true;
            LocalDate giorno = inizio;

            while (!giorno.isAfter(fine) && idoneo) {
                if (!verificaDisponibilitaGiorno(matricolaAssente, candidato.getMatricola(), giorno)) {
                    idoneo = false;
                }
                giorno = giorno.plusDays(1);
            }

            if (idoneo) sostituti.add(candidato);
        }
        return sostituti;
    }

    // Controlla se il candidato può coprire tutti i turni dell'assente in un giorno specifico
    private boolean verificaDisponibilitaGiorno(String matricolaAssente, String matricolaCandidato, LocalDate data) {
        String giornoIta = convertiGiorno(data);
        String queryTurni = "SELECT orainizio, orafine FROM turno " +
                "WHERE codmedico = ? AND LOWER(giornosettimana) = LOWER(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(queryTurni)) {
            pstmt.setString(1, matricolaAssente);
            pstmt.setString(2, giornoIta);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LocalTime oraInizio = rs.getTime("orainizio").toLocalTime();
                    LocalTime oraFine = rs.getTime("orafine").toLocalTime();
                    if (!verificaDisponibilita(matricolaCandidato, data, oraInizio, oraFine)) {
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean verificaDisponibilita(String matricolaMedico, LocalDate data, LocalTime oraInizio, LocalTime oraFine) {
        String giornoIta = convertiGiorno(data);

        // Verifica che il medico abbia un turno attivo che copre la fascia richiesta
        String queryTurno = "SELECT COUNT(*) FROM turno " +
                "WHERE codmedico = ? AND LOWER(giornosettimana) = LOWER(?) " +
                "AND orainizio <= ? AND orafine >= ?";

        try (PreparedStatement pstmt = conn.prepareStatement(queryTurno)) {
            pstmt.setString(1, matricolaMedico);
            pstmt.setString(2, giornoIta);
            pstmt.setTime(3, Time.valueOf(oraInizio));
            pstmt.setTime(4, Time.valueOf(oraFine));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Verifica che non ci siano prestazioni sovrapposte nella stessa data
        String querySovrapposizione = "SELECT COUNT(*) FROM prestazione " +
                "WHERE codmedico = ? AND data = ? " +
                "AND NOT (orafine <= ? OR orainizio >= ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(querySovrapposizione)) {
            pstmt.setString(1, matricolaMedico);
            pstmt.setDate(2, Date.valueOf(data));
            pstmt.setTime(3, Time.valueOf(oraInizio));
            pstmt.setTime(4, Time.valueOf(oraFine));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Verifica che il medico non sia in un periodo di assenza registrato
        String queryAssenza = "SELECT COUNT(*) FROM assenza_medico " +
                "WHERE codmedico = ? AND ? BETWEEN datainizio AND datafine";

        try (PreparedStatement pstmt = conn.prepareStatement(queryAssenza)) {
            pstmt.setString(1, matricolaMedico);
            pstmt.setDate(2, Date.valueOf(data));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    // Converte un DayOfWeek nel nome italiano del giorno usato nel DB
    private String convertiGiorno(LocalDate data) {
        switch (data.getDayOfWeek()) {
            case MONDAY:    return "Lunedì";
            case TUESDAY:   return "Martedì";
            case WEDNESDAY: return "Mercoledì";
            case THURSDAY:  return "Giovedì";
            case FRIDAY:    return "Venerdì";
            case SATURDAY:  return "Sabato";
            case SUNDAY:    return "Domenica";
            default:        return "";
        }
    }
}