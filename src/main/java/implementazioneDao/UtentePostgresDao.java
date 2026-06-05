package implementazioneDao;

import dao.UtenteDAO;
import database_connection.ConnessioneDatabase;
import model.Utente;
import model.Admin;
import model.Medico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtentePostgresDAO implements UtenteDAO {

    private Connection conn;

    // Costruttore: recupera la connessione centralizzata
    public UtentePostgresDAO() {
        this.conn = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public String verificaLogin(String username, String password) {
        // Query per verificare se esistono le credenziali inserite
        String query = "SELECT tipoUtente FROM UTENTE WHERE username = ? AND password = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Sostituisce i punti interrogativi con i valori passati come parametro
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Se trova una corrispondenza, restituisce il tipo di utente
                if (rs.next()) {
                    return rs.getString("tipoUtente").toLowerCase();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Restituisce errore se le credenziali non sono corrette
        return "errore";
    }

    @Override
    public Utente getUtenteByUsername(String username) {
        // Query per ottenere i dati dell'utente dal suo username
        String query = "SELECT username, password, tipoUtente FROM UTENTE WHERE username = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String password = rs.getString("password");
                    String tipo = rs.getString("tipoUtente");

                    // Crea l'oggetto specifico in base al tipo memorizzato nel database
                    if (tipo.equalsIgnoreCase("ADMIN")) {
                        return new Admin(username, password);
                    } else if (tipo.equalsIgnoreCase("MEDICO")) {
                        return new Medico(username, password, null, null);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}