package implementazioneDao;

import dao.MedicoDAO;
import database_connection.ConnessioneDatabase;
import model.Medico;
import model.Reparto;
import java.sql.*;
import java.time.LocalDate;
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
        // Query che unisce la ricerca per reparto ed esclude chi è già assente nello stesso intervallo
        String query = "SELECT m.matricola, m.username, m.idreparto, r.nome AS nome_reparto " +
                "FROM medico m " +
                "LEFT JOIN reparto r ON m.idreparto = r.idreparto " +
                "WHERE m.idreparto = (SELECT idreparto FROM medico WHERE matricola = ?) " +
                "AND m.matricola <> ? " +
                "AND m.matricola NOT IN (" +
                "    SELECT codmedico FROM assenza_medico " +
                "    WHERE NOT (datafine < ? OR datainizio > ?)" +
                ")";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, matricolaAssente);
            pstmt.setString(2, matricolaAssente);
            pstmt.setDate(3, Date.valueOf(inizio));
            pstmt.setDate(4, Date.valueOf(fine));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Reparto rep = new Reparto(rs.getInt("idreparto"), rs.getString("nome_reparto"));
                    sostituti.add(new Medico(rs.getString("username"), "", rs.getString("matricola"), rep));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sostituti;
    }

        @Override
        public boolean verificaDisponibilita(String matricolaMedico, java.time.LocalDate data, java.time.LocalTime oraInizio, java.time.LocalTime oraFine) {
            // 1. Traduciamo la data nel giorno della settimana in italiano (Compatibile Java 8)
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

            // 2. CONTROLLO TURNO: Il medico deve avere un turno che INIZIA prima (o uguale) e FINISCE dopo (o uguale) rispetto alla fascia richiesta
            String queryTurno = "SELECT COUNT(*) FROM turno WHERE codmedico = ? AND LOWER(giornosettimana) = LOWER(?) AND orainizio <= ? AND orafine >= ?";
            try (PreparedStatement pstmt = conn.prepareStatement(queryTurno)) {
                pstmt.setString(1, matricolaMedico);
                pstmt.setString(2, giornoSettimanaInItaliano);
                pstmt.setTime(3, java.sql.Time.valueOf(oraInizio));
                pstmt.setTime(4, java.sql.Time.valueOf(oraFine));

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        return false; // Il medico non è in turno in quel giorno/orario
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

            // 3. CONTROLLO SOVRAPPOSIZIONI: Il medico non deve avere visite in corso in quella data e orario
            String querySovrapposizione =
                    "SELECT COUNT(*) FROM prestazione p " +
                            "JOIN ricovero r ON p.idricovero = r.idricovero " +
                            "WHERE p.codmedico = ? AND r.datainizio = ? " +
                            "  AND NOT (p.orafine <= ? OR p.orainizio >= ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(querySovrapposizione)) {
                pstmt.setString(1, matricolaMedico);
                pstmt.setDate(2, java.sql.Date.valueOf(data));
                pstmt.setTime(3, java.sql.Time.valueOf(oraInizio));
                pstmt.setTime(4, java.sql.Time.valueOf(oraFine));

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return false; // C'è una sovrapposizione, quindi NON è disponibile
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

    String queryAssenza = "SELECT COUNT(*) FROM assenzamedico WHERE codmedico = ? AND ? BETWEEN datainizio AND datafine";
    try (PreparedStatement pstmt = conn.prepareStatement(queryAssenza)) {
        pstmt.setString(1, matricolaMedico);
        pstmt.setDate(2, java.sql.Date.valueOf(data));
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) return false;
        }
    } catch (SQLException e) { e.printStackTrace(); }


            return true; // Ha il turno, non ha impegni e non è assente: DISPONIBILE!
        }
    }
