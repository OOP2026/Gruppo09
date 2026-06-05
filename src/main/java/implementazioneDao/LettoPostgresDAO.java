package implementazioneDao;

import dao.LettoDAO;
import database_connection.ConnessioneDatabase;
import model.Letto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LettoPostgresDAO implements LettoDAO {
    private Connection conn;

    public LettoPostgresDAO() {
        this.conn = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public List<Letto> getAllLetti() {
        List<Letto> lista = new ArrayList<>();
        String query = "SELECT l.codiceletto, " +
                "CASE WHEN EXISTS (" +
                "  SELECT 1 FROM ricovero r " +
                "  WHERE r.codiceletto = l.codiceletto " +
                "  AND r.datadimissioneeffettiva IS NULL" +
                ") THEN true ELSE false END AS occupato " +
                "FROM letto l ORDER BY l.codiceletto ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Letto letto = new Letto(rs.getInt("codiceletto"));
                // Imposta lo stato occupato calcolato direttamente dalla query
                letto.setOccupato(rs.getBoolean("occupato"));
                lista.add(letto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Letto> getLettiPerReparto(int idReparto) {
        List<Letto> lista = new ArrayList<>();

        // Recupera i letti del reparto con il loro stato attuale di occupazione
        String query = "SELECT l.codiceletto, " +
                "CASE WHEN EXISTS (" +
                "  SELECT 1 FROM ricovero r " +
                "  WHERE r.codiceletto = l.codiceletto " +
                "  AND r.datadimissioneeffettiva IS NULL" +
                ") THEN true ELSE false END AS occupato " +
                "FROM letto l " +   
                "JOIN stanza s ON l.idstanza = s.idstanza " +
                "WHERE s.idreparto = ? " +
                "ORDER BY l.codiceletto ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idReparto);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Letto letto = new Letto(rs.getInt("codiceletto"));
                    letto.setOccupato(rs.getBoolean("occupato"));
                    lista.add(letto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}