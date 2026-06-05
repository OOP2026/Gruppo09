package implementazioneDao;

import dao.PrestazioneDAO;
import database_connection.ConnessioneDatabase;
import model.Medico;
import model.Prestazione;
import model.Ricovero;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PrestazionePostgresDAO implements PrestazioneDAO {
    private Connection conn;

    public PrestazionePostgresDAO() {
        this.conn = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public List<Prestazione> getAgendaGiornaliera(String matricolaMedico) {
        List<Prestazione> agenda = new ArrayList<>();

        // Query con JOIN per verificare che la data del ricovero sia OGGI
        String query = "SELECT p.idprestazione, p.tipo, p.orainizio, p.orafine, p.esito, p.idricovero " +
                "FROM prestazione p " +
                "JOIN ricovero r ON p.idricovero = r.idricovero " +
                "JOIN medico m ON p.codmedico = m.matricola " +
                "WHERE m.username = ? AND r.datainizio = CURRENT_DATE " +
                "ORDER BY p.orainizio ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, matricolaMedico);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LocalTime oraInizio = rs.getTime("orainizio").toLocalTime();
                    LocalTime oraFine = rs.getTime("orafine").toLocalTime();

                    // Istanziamo gli oggetti composti richiesti dal costruttore di Prestazione
                    Medico medico = new Medico("", "", matricolaMedico, null);
                    Ricovero ricovero = new Ricovero(rs.getInt("idricovero"), null, null, null, null, null, null);

                    // Creiamo l'oggetto prestazione usando il tuo costruttore
                    String tipo = rs.getString("tipo");
                    Prestazione p = new Prestazione(tipo, oraInizio, oraFine, medico, ricovero);

                    // Impostiamo l'ID e l'esito recuperati dal DB
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

        // Query che estrae i prossimi 7 giorni (oggi compreso) ordinati per data e ora
        String query = "SELECT p.idprestazione, p.tipo, p.orainizio, p.orafine, p.esito, p.idricovero, r.datainizio " +
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

                    Medico medico = new Medico(usernameMedico, "", "", null);
                    Ricovero ricovero = new Ricovero(rs.getInt("idricovero"), null, null, null, null, null, null);

                    // Preleviamo la data dal DB e la passiamo al ricovero
                    // Controlla il nome del setter nella tua classe Ricovero (es. setDataInizio o simile)
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
        // 1. Recuperiamo la matricola del medico dallo username
        String matricolaMedico = null;
        String queryMedico = "SELECT matricola FROM medico WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(queryMedico)) {
            pstmt.setString(1, usernameMedico);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) matricolaMedico = rs.getString("matricola");
            }
        } catch (SQLException e) {
            return "Errore nel recupero dei dati del medico: " + e.getMessage();
        }
        if (matricolaMedico == null) return "Medico non trovato.";

        // 2. RECUPERO DATA DEL RICOVERO E TRADUZIONE IN GIORNO DELLA SETTIMANA
        String giornoSettimanaInItaliano = null;
        String queryDataRicovero = "SELECT datainizio FROM ricovero WHERE idricovero = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(queryDataRicovero)) {
            pstmt.setInt(1, idRicovero);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    java.time.LocalDate dataRic = rs.getDate("datainizio").toLocalDate();

                    switch (dataRic.getDayOfWeek()) {
                        case MONDAY:
                            giornoSettimanaInItaliano = "Lunedì";
                            break;
                        case TUESDAY:
                            giornoSettimanaInItaliano = "Martedì";
                            break;
                        case WEDNESDAY:
                            giornoSettimanaInItaliano = "Mercoledì";
                            break;
                        case THURSDAY:
                            giornoSettimanaInItaliano = "Giovedì";
                            break;
                        case FRIDAY:
                            giornoSettimanaInItaliano = "Venerdì";
                            break;
                        case SATURDAY:
                            giornoSettimanaInItaliano = "Sabato";
                            break;
                        case SUNDAY:
                            giornoSettimanaInItaliano = "Domenica";
                            break;
                    }
                } else {
                    return "Errore: ID Ricovero non esistente nel database.";
                }
            }
        } catch (SQLException e) {
            return "Errore nel recupero della data del ricovero: " + e.getMessage();
        }
        // 3. CONTROLLO TURNO LAVORATIVO (Ora tiene conto anche del giorno esatto!)
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

        // 4. CONTROLLO SOVRAPPOSIZIONE TEMPORALE
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

        // 5. INSERIMENTO FINALE
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