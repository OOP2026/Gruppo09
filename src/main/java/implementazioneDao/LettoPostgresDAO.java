package implementazioneDao;

import dao.LettoDAO;
import database_connection.ConnessioneDatabase;
import model.Letto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String query = "SELECT codiceletto FROM letto ORDER BY codiceletto ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Letto(rs.getInt("codiceletto")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}