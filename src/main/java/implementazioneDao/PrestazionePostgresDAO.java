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

/**
 * Implementazione PostgreSQL del DAO per l'accesso ai dati delle prestazioni mediche.
 * Gestisce l'agenda giornaliera e settimanale, la registrazione di nuove prestazioni
 * con controlli di turno e sovrapposizione, e l'aggiornamento degli esiti.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class PrestazionePostgresDAO implements PrestazioneDAO {
    private Connection conn;

    public PrestazionePostgresDAO() {
        this.conn = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public List<Prestazione> getAgendaGiornaliera(String matricolaMedico) {
        List<Prestazione> agenda = new ArrayList<>();
        String query = "SELECT p.idprestazione, p.tipo, p.data, p.orainizio, p.orafine, " +
                "p.esito, p.idricovero, m.username " +
                "FROM prestazione p " +
                "JOIN medico m ON p.codmedico = m.matricola " +
                "WHERE p.codmedico = ? AND p.data = CURRENT_DATE " +
                "ORDER BY p.orainizio ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, matricolaMedico);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate data = rs.getDate("data").toLocalDate();
                    LocalTime oraInizio = rs.getTime("orainizio").toLocalTime();
                    LocalTime oraFine = rs.getTime("orafine").toLocalTime();

                    Medico medico = new Medico(rs.getString("username"), "", matricolaMedico, null);
                    Ricovero ricovero = new Ricovero(rs.getInt("idricovero"), null, null, null, null, null, null);

                    Prestazione p = new Prestazione(rs.getString("tipo"), data, oraInizio, oraFine, medico, ricovero);
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
    public List<Prestazione> getAgendaSettimanale(String matricolaMedico) {
        List<Prestazione> agenda = new ArrayList<>();
        String query = "SELECT p.idprestazione, p.tipo, p.data, p.orainizio, p.orafine, " +
                "p.esito, p.idricovero, m.username " +
                "FROM prestazione p " +
                "JOIN medico m ON p.codmedico = m.matricola " +
                "WHERE p.codmedico = ? " +
                "AND p.data BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '6 days' " +
                "ORDER BY p.data ASC, p.orainizio ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, matricolaMedico);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate data = rs.getDate("data").toLocalDate();
                    LocalTime oraInizio = rs.getTime("orainizio").toLocalTime();
                    LocalTime oraFine = rs.getTime("orafine").toLocalTime();

                    Medico medico = new Medico(rs.getString("username"), "", matricolaMedico, null);
                    Ricovero ricovero = new Ricovero(rs.getInt("idricovero"), null, null, null, null, null, null);

                    Prestazione p = new Prestazione(rs.getString("tipo"), data, oraInizio, oraFine, medico, ricovero);
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
    public String registraPrestazione(String matricolaMedico, int idRicovero, String tipo,
                                      LocalDate data, LocalTime oraInizio, LocalTime oraFine, String esito) {
        String giornoIta = convertiGiorno(data);

        // Verifica che la fascia oraria rientri in un turno del medico nel giorno indicato
        String queryTurno = "SELECT orainizio, orafine FROM turno " +
                "WHERE codmedico = ? AND LOWER(giornosettimana) = LOWER(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(queryTurno)) {
            pstmt.setString(1, matricolaMedico);
            pstmt.setString(2, giornoIta);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    return "Errore: nessun turno previsto per " + giornoIta;
                }
                LocalTime turnoInizio = rs.getTime("orainizio").toLocalTime();
                LocalTime turnoFine = rs.getTime("orafine").toLocalTime();
                if (oraInizio.isBefore(turnoInizio) || oraFine.isAfter(turnoFine)) {
                    return "Errore: orario fuori dal turno (" + turnoInizio + " - " + turnoFine + ")";
                }
            }
        } catch (SQLException e) {
            return "Errore nel controllo del turno: " + e.getMessage();
        }

        // Verifica che non ci siano prestazioni sovrapposte per lo stesso medico nella stessa data
        String querySovrapposizione = "SELECT COUNT(*) FROM prestazione " +
                "WHERE codmedico = ? AND data = ? " +
                "AND NOT (orafine <= ? OR orainizio >= ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(querySovrapposizione)) {
            pstmt.setString(1, matricolaMedico);
            pstmt.setDate(2, Date.valueOf(data));
            pstmt.setTime(3, Time.valueOf(oraInizio));
            pstmt.setTime(4, Time.valueOf(oraFine));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return "Errore: sovrapposizione con un'altra prestazione dello stesso medico";
                }
            }
        } catch (SQLException e) {
            return "Errore nel controllo sovrapposizioni: " + e.getMessage();
        }

        // Inserisce la prestazione nel database
        String queryInsert = "INSERT INTO prestazione (tipo, data, orainizio, orafine, esito, idricovero, codmedico) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(queryInsert)) {
            pstmt.setString(1, tipo);
            pstmt.setDate(2, Date.valueOf(data));
            pstmt.setTime(3, Time.valueOf(oraInizio));
            pstmt.setTime(4, Time.valueOf(oraFine));
            pstmt.setString(5, (esito == null || esito.trim().isEmpty()) ? null : esito);
            pstmt.setInt(6, idRicovero);
            pstmt.setString(7, matricolaMedico);
            pstmt.executeUpdate();
            return "OK";
        } catch (SQLException e) {
            return "Errore durante l'inserimento: " + e.getMessage();
        }
    }

    @Override
    public boolean aggiornaEsito(int idPrestazione, String nuovoEsito) {
        // Chiama la procedura aggiorna_esito_prestazione invece della query diretta
        String query = "CALL aggiorna_esito_prestazione(?, ?)";

        try (CallableStatement cstmt = conn.prepareCall(query)) {
            cstmt.setInt(1, idPrestazione);
            cstmt.setString(2, nuovoEsito);
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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