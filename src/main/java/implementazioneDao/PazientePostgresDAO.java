package implementazioneDao;

import dao.PazienteDAO;
import database_connection.ConnessioneDatabase;
import model.Paziente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PazientePostgresDAO implements PazienteDAO {

    private Connection conn;

    public PazientePostgresDAO() {
        // Recupera la connessione centralizzata
        this.conn = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public boolean inserisciPaziente(Paziente paziente) {
        // Clausola ON CONFLICT per gestire inserimento e modifica in un'unica operazione
        String query = "INSERT INTO paziente (codicefiscale, nome, cognome) VALUES (?, ?, ?) " +
                "ON CONFLICT (codicefiscale) " +
                "DO UPDATE SET nome = EXCLUDED.nome, cognome = EXCLUDED.cognome";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, paziente.getCodiceFiscale());
            pstmt.setString(2, paziente.getNome());
            pstmt.setString(3, paziente.getCognome());

            int righeCoinvolte = pstmt.executeUpdate();
            return righeCoinvolte > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Paziente> getAllPazienti() {
        List<Paziente> lista = new ArrayList<>();
        String query = "SELECT codicefiscale, nome, cognome FROM paziente";

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Estratto utilizzando i nomi colonna minuscoli coerenti con PostgreSQL
                String cf = rs.getString("codicefiscale");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");

                Paziente p = new Paziente(cf, nome, cognome);
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}