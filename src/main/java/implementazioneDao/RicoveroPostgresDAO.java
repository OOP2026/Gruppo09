package implementazioneDao;

import dao.RicoveroDAO;
import database_connection.ConnessioneDatabase;
import model.Ricovero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RicoveroPostgresDAO implements RicoveroDAO {
    private Connection conn;

    public RicoveroPostgresDAO() {
        // Recupera la connessione centralizzata dal Singleton
        this.conn = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public boolean checkSovrapposizione(Ricovero ricovero) {
        // Verifica conflitti con degenze attive sullo stesso letto nell'intervallo indicato
        String query = "SELECT COUNT(*) FROM ricovero WHERE codiceletto = ? AND datadimissioneeffettiva IS NULL " +
                "AND NOT (datadimissioneprevista < ? OR datainizio > ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, ricovero.getLetto().getIdLetto());
            pstmt.setDate(2, Date.valueOf(ricovero.getDataInizio()));
            pstmt.setDate(3, Date.valueOf(ricovero.getDataDimissionePrevista()));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean inserisciRicovero(Ricovero ricovero) {
        String query = "INSERT INTO ricovero (idricovero, codpaziente, codiceletto, datainizio, orainizio, datadimissioneprevista, oradimissioneprevista) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, ricovero.getIdRicovero());
            pstmt.setString(2, ricovero.getPaziente().getCodiceFiscale());
            pstmt.setInt(3, ricovero.getLetto().getIdLetto());
            pstmt.setDate(4, Date.valueOf(ricovero.getDataInizio()));
            pstmt.setTime(5, Time.valueOf(ricovero.getOraInizio()));
            pstmt.setDate(6, Date.valueOf(ricovero.getDataDimissionePrevista()));
            pstmt.setTime(7, Time.valueOf(ricovero.getOraDimissionePrevista()));

            int righeInserite = pstmt.executeUpdate();
            return righeInserite > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getRicoveriAttiviPerComboBox() {
        List<String> lista = new ArrayList<>();

        // Query con JOIN che seleziona solo i ricoveri NON ancora dimessi
        String query = "SELECT r.idricovero, p.nome, p.cognome, p.codicefiscale " +
                "FROM ricovero r " +
                "JOIN paziente p ON r.codpaziente = p.codicefiscale " +
                "WHERE r.datadimissioneeffettiva IS NULL " +
                "ORDER BY r.idricovero DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Formatta il testo identificativo da mostrare nel menu a tendina della GUI
                String elemento = rs.getInt("idricovero") + " | " +
                        rs.getString("nome") + " " +
                        rs.getString("cognome") + " (" +
                        rs.getString("codicefiscale") + ")";
                lista.add(elemento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}