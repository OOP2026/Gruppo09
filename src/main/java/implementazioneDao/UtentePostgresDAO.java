package implementazioneDao;

import dao.UtenteDAO;
import database_connection.ConnessioneDatabase;
import model.Utente;
import model.Admin;
import model.Medico;
import model.Reparto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementazione PostgreSQL del DAO per l'autenticazione degli utenti.
 * Gestisce la verifica delle credenziali e il caricamento dell'oggetto utente
 * completo con istanziazione polimorfica di Admin o Medico.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class UtentePostgresDAO implements UtenteDAO {
    private Connection conn;

    public UtentePostgresDAO() {
        this.conn = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public String verificaLogin(String username, String password) {
        // Controlla la validità delle credenziali inserite nel pannello di login
        String query = "SELECT tipoutente FROM utente WHERE username = ? AND password = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("tipoutente").toLowerCase();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "errore";
    }

    @Override
    public Utente getUtenteByUsername(String username) {
        // JOIN tra utente, medico e reparto per caricare tutti i dati in una sola query
        String query = "SELECT u.username, u.password, u.tipoutente, m.matricola, m.idreparto, r.nome AS nome_reparto " +
                "FROM utente u " +
                "LEFT JOIN medico m ON u.username = m.username " +
                "LEFT JOIN reparto r ON m.idreparto = r.idreparto " +
                "WHERE u.username = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String password = rs.getString("password");
                    String tipo = rs.getString("tipoutente");

                    // Istanziazione polimorfica dell'utente in base al tipo trovato nel DB
                    if (tipo.equalsIgnoreCase("ADMIN")) {
                        return new Admin(username, password);
                    } else if (tipo.equalsIgnoreCase("MEDICO")) {
                        Reparto reparto = null;
                        if (rs.getObject("idreparto") != null) {
                            reparto = new Reparto(rs.getInt("idreparto"), rs.getString("nome_reparto"));
                        }
                        return new Medico(username, password, rs.getString("matricola"), reparto);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}