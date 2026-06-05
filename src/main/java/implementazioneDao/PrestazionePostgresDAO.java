package implementazioneDao;

import dao.PrestazioneDAO;
import database_connection.ConnessioneDatabase;
import model.Medico;
import model.Prestazione;
import model.Ricovero;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PrestazionePostgresDAO implements PrestazioneDAO {
    private Connection conn;

    public PrestazionePostgresDAO() {
        // Recupera la connessione centralizzata dal Singleton
        this.conn = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public List<Prestazione> getAgendaGiornaliera(String matricolaMedico) {
        List<Prestazione> agenda = new ArrayList<>();

        // Query corretta per filtrare esplicitamente sulla matricola del medico
        String query = "SELECT p.idprestazione, p.tipo, p.orainizio, p.orafine, p.esito, p.idricovero, m.username " +
                "FROM prestazione p " +
                "JOIN ricovero r ON p.idricovero = r.idricovero " +
                "JOIN medico m ON p.codmedico = m.matricola " +
                "WHERE m.matricola = ? AND r.datainizio = CURRENT_DATE " +
                "ORDER BY p.orainizio ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, matricolaMedico);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LocalTime oraInizio = rs.getTime("orainizio").toLocalTime();
                    LocalTime oraFine = rs.getTime("orafine").toLocalTime();

                    // Ricostruisce l'associazione con il medico e il ricovero
                    Medico medico = new Medico(rs.getString("username"), "", matricolaMedico, null);
                    Ricovero ricovero = new Ricovero(rs.getInt("idricovero"), null, null, null, null, null, null);

                    String tipo = rs.getString("tipo");
                    Prestazione p = new Prestazione(tipo, oraInizio, oraFine, medico, ricovero);

                    p.setIdPrestazione(rs.getInt("idprestazione"));
                    p.setEsito(rs.getString("esito"));

                    agenda.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agenda;
    }

    @Override
    public List<Prestazione> getAgendaSettimanale(String usernameMedico) {
        List<Prestazione> agenda = new ArrayList<>();

        // Estrae le prestazioni comprese nell'intervallo dei prossimi 7 giorni
        String query = "SELECT p.idprestazione, p.tipo, p.orainizio, p.orafine, p.esito, p.idricovero, r.datainizio, m.matricola " +
                "FROM prestazione p " +
                "JOIN ricovero r ON p.idricovero = r.idricovero " +
                "JOIN medico m ON p.codmedico = m.matricola " +
                "WHERE m.username = ? AND r.datainizio BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '6 days' " +
                "ORDER BY r.datainizio ASC, p.orainizio ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, usernameMedico);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LocalTime oraInizio = rs.getTime("orainizio").toLocalTime();
                    LocalTime oraFine = rs.getTime("orafine").toLocalTime();

                    Medico medico = new Medico(usernameMedico, "", rs.getString("matricola"), null);
                    Ricovero ricovero = new Ricovero(rs.getInt("idricovero"), null, null, null, null, null, null);

                    if (rs.getDate("datainizio") != null) {
                        ricovero.setDataInizio(rs.getDate("datainizio").toLocalDate());
                    }

                    String tipo = rs.getString("tipo");
                    Prestazione p = new Prestazione(tipo, oraInizio, oraFine, medico, ricovero);
                    p.setIdPrestazione(rs.getInt("idprestazione"));
                    p.setEsito(rs.getString("esito"));

                    agenda.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agenda;
    }

    @Override
    public String registraPrestazione(String usernameMedico, int idRicovero, String tipo, LocalTime oraInizio, LocalTime oraFine, String esito) {
        String matricolaMedico = null;

        // 1. Recupera la matricola del medico partendo dallo username di sessione
        String queryMedico = "SELECT matricola FROM medico WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(queryMedico)) {
            pstmt.setString(1, usernameMedico);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    matricolaMedico = rs.getString("matricola");
                }
            }
        } catch (SQLException e) {
            return "Errore nel recupero dei dati del medico: " + e.getMessage();
        }
        if (matricolaMedico == null) return "Medico non trovato.";

        // 2. Determina il giorno della settimana associato alla data del ricovero
        String giornoSettimanaInItaliano = null;
        String queryDataRicovero = "SELECT datainizio FROM ricovero WHERE idricovero = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(queryDataRicovero)) {
            pstmt.setInt(1, idRicovero);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate dataRic = rs.getDate("datainizio").toLocalDate();

                    switch (dataRic.getDayOfWeek()) {
                        case MONDAY:    giornoSettimanaInItaliano = "Lunedì"; break;
                        case TUESDAY:   giornoSettimanaInItaliano = "Martedì"; break;
                        case WEDNESDAY: giornoSettimanaInItaliano = "Mercoledì"; break;
                        case THURSDAY:  giornoSettimanaInItaliano = "Giovedì"; break;
                        case FRIDAY:    giornoSettimanaInItaliano = "Venerdì"; break;
                        case SATURDAY:  giornoSettimanaInItaliano = "Sabato"; break;
                        case SUNDAY:    giornoSettimanaInItaliano = "Domenica"; break;
                    }
                } else {
                    return "Errore: ID Ricovero non esistente nel database.";
                }
            }
        } catch (SQLException e) {
            return "Errore nel recupero della data del ricovero: " + e.getMessage();
        }

        // 3. Verifica che l'orario della prestazione rientri interamente nel turno del medico
        String queryTurno = "SELECT orainizio, orafine FROM turno WHERE codmedico = ? AND LOWER(giornosettimana) = LOWER(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(queryTurno)) {
            pstmt.setString(1, matricolaMedico);
            pstmt.setString(2, giornoSettimanaInItaliano);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    LocalTime turnoInizio = rs.getTime("orainizio").toLocalTime();
                    LocalTime turnoFine = rs.getTime("orafine").toLocalTime();

                    if (oraInizio.isBefore(turnoInizio) || oraFine.isAfter(turnoFine)) {
                        return "Errore: L'orario inserito è fuori dal turno lavorativo di " + giornoSettimanaInItaliano + " (" + turnoInizio + " - " + turnoFine + ").";
                    }
                } else {
                    return "Errore: Il medico non ha alcun turno lavorativo pianificato per il giorno: " + giornoSettimanaInItaliano;
                }
            }
        } catch (SQLException e) {
            return "Errore nel controllo del turno: " + e.getMessage();
        }

        // 4. Controllo categorico sulle sovrapposizioni temporali per lo stesso medico
        String querySovrapposizione =
                "SELECT COUNT(*) FROM prestazione p " +
                        "JOIN ricovero r ON p.idricovero = r.idricovero " +
                        "WHERE p.codmedico = ? " +
                        "  AND r.datainizio = (SELECT datainizio FROM ricovero WHERE idricovero = ?) " +
                        "  AND NOT (p.orafine <= ? OR p.orainizio >= ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(querySovrapposizione)) {
            pstmt.setString(1, matricolaMedico);
            pstmt.setInt(2, idRicovero);
            pstmt.setTime(3, java.sql.Time.valueOf(oraInizio));
            pstmt.setTime(4, java.sql.Time.valueOf(oraFine));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return "Errore: Il medico ha già un'altra prestazione che si sovrappone a questo orario.";
                }
            }
        } catch (SQLException e) {
            return "Errore nel controllo delle sovrapposizioni: " + e.getMessage();
        }

        // 5. Inserimento finale della prestazione validata
        String queryInsert = "INSERT INTO prestazione (tipo, orainizio, orafine, esito, idricovero, codmedico) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(queryInsert)) {
            pstmt.setString(1, tipo);
            pstmt.setTime(2, java.sql.Time.valueOf(oraInizio));
            pstmt.setTime(3, java.sql.Time.valueOf(oraFine));
            pstmt.setString(4, (esito == null || esito.trim().isEmpty()) ? null : esito);
            pstmt.setInt(5, idRicovero);
            pstmt.setString(6, matricolaMedico);

            pstmt.executeUpdate();
            return "OK";
        } catch (SQLException e) {
            return "Errore durante l'inserimento nel database: " + e.getMessage();
        }
    }
}