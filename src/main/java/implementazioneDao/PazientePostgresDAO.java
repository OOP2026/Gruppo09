package implementazioneDao;

import dao.PazienteDAO;
import database_connection.ConnessioneDatabase;
import model.Paziente;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PazientePostgresDAO implements PazienteDAO {
    private Connection conn;

    public PazientePostgresDAO() {
        this.conn = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public boolean inserisciPaziente(Paziente paziente) {
        // Chiama la procedura inserisci_paziente invece della query diretta
        String query = "CALL inserisci_paziente(?, ?, ?)";

        try (CallableStatement cstmt = conn.prepareCall(query)) {
            cstmt.setString(1, paziente.getCodiceFiscale());
            cstmt.setString(2, paziente.getNome());
            cstmt.setString(3, paziente.getCognome());
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Paziente> getAllPazienti() {
        List<Paziente> lista = new ArrayList<>();
        String query = "SELECT codicefiscale, nome, cognome FROM paziente ORDER BY cognome ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Paziente(
                        rs.getString("codicefiscale"),
                        rs.getString("nome"),
                        rs.getString("cognome")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Paziente> getPazientiInScadenza(LocalDate data) {
        List<Paziente> lista = new ArrayList<>();

        // Recupera i pazienti la cui dimissione prevista cade nella data indicata
        String query = "SELECT p.codicefiscale, p.nome, p.cognome " +
                "FROM paziente p " +
                "JOIN ricovero r ON p.codicefiscale = r.codpaziente " +
                "WHERE r.datadimissioneprevista = ? " +
                "AND r.datadimissioneeffettiva IS NULL " +
                "ORDER BY p.cognome ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDate(1, Date.valueOf(data));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Paziente(
                            rs.getString("codicefiscale"),
                            rs.getString("nome"),
                            rs.getString("cognome")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}