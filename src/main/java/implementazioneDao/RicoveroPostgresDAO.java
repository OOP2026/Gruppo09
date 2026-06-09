package implementazioneDao;

import dao.RicoveroDAO;
import database_connection.ConnessioneDatabase;
import model.Letto;
import model.Paziente;
import model.Ricovero;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione PostgreSQL del DAO per l'accesso ai dati dei ricoveri ospedalieri.
 * Il controllo di sovrapposizione usa COALESCE per gestire sia i ricoveri attivi
 * che quelli già dimessi. L'inserimento avviene tramite procedura SQL.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class RicoveroPostgresDAO implements RicoveroDAO {
    private Connection conn;

    public RicoveroPostgresDAO() {
        this.conn = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public boolean checkSovrapposizione(Ricovero ricovero) {
        // Controlla se esiste un ricovero attivo sullo stesso letto con date sovrapposte
        // COALESCE usa la dimissione effettiva se presente, altrimenti quella prevista
        String query = "SELECT COUNT(*) FROM ricovero " +
                "WHERE codiceletto = ? " +
                "AND idricovero <> ? " +
                "AND NOT (COALESCE(datadimissioneeffettiva, datadimissioneprevista) < ? " +
                "      OR datainizio > ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, ricovero.getLetto().getIdLetto());
            pstmt.setInt(2, ricovero.getIdRicovero());
            pstmt.setDate(3, Date.valueOf(ricovero.getDataInizio()));
            pstmt.setDate(4, Date.valueOf(ricovero.getDataDimissionePrevista()));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean inserisciRicovero(Ricovero ricovero) {
        // Chiama la procedura inserisci_ricovero invece della query diretta
        String query = "CALL inserisci_ricovero(?, ?, ?, ?, ?, ?)";

        try (CallableStatement cstmt = conn.prepareCall(query)) {
            cstmt.setString(1, ricovero.getPaziente().getCodiceFiscale());
            cstmt.setInt(2, ricovero.getLetto().getIdLetto());
            cstmt.setDate(3, Date.valueOf(ricovero.getDataInizio()));
            cstmt.setTime(4, Time.valueOf(ricovero.getOraInizio()));
            cstmt.setDate(5, Date.valueOf(ricovero.getDataDimissionePrevista()));
            cstmt.setTime(6, Time.valueOf(ricovero.getOraDimissionePrevista()));
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getRicoveriAttiviPerComboBox() {
        List<String> lista = new ArrayList<>();
        String query = "SELECT r.idricovero, p.nome, p.cognome, p.codicefiscale " +
                "FROM ricovero r " +
                "JOIN paziente p ON r.codpaziente = p.codicefiscale " +
                "WHERE r.datadimissioneeffettiva IS NULL " +
                "ORDER BY r.idricovero DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Formato: "ID | Nome Cognome (CF)" leggibile nella ComboBox
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

    @Override
    public List<Ricovero> getRicoveriInScadenza(LocalDate data) {
        List<Ricovero> lista = new ArrayList<>();
        // Recupera i ricoveri attivi con dimissione prevista nella data indicata
        String query = "SELECT r.idricovero, r.datainizio, r.orainizio, " +
                "r.datadimissioneprevista, r.oradimissioneprevista, " +
                "p.codicefiscale, p.nome, p.cognome, r.codiceletto " +
                "FROM ricovero r " +
                "JOIN paziente p ON r.codpaziente = p.codicefiscale " +
                "WHERE r.datadimissioneprevista = ? " +
                "AND r.datadimissioneeffettiva IS NULL " +
                "ORDER BY r.orainizio ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDate(1, Date.valueOf(data));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Paziente paziente = new Paziente(
                            rs.getString("codicefiscale"),
                            rs.getString("nome"),
                            rs.getString("cognome")
                    );
                    Letto letto = new Letto(rs.getInt("codiceletto"));
                    Ricovero ricovero = new Ricovero(
                            rs.getInt("idricovero"),
                            paziente,
                            letto,
                            rs.getDate("datainizio").toLocalDate(),
                            rs.getTime("orainizio").toLocalTime(),
                            rs.getDate("datadimissioneprevista").toLocalDate(),
                            rs.getTime("oradimissioneprevista").toLocalTime()
                    );
                    lista.add(ricovero);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}