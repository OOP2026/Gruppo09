package implementazioneDao;

import dao.AssenzaMedicoDAO;
import database_connection.ConnessioneDatabase;
import java.sql.*;
import java.time.LocalDate;

/**
 * Implementazione PostgreSQL del DAO per la gestione delle assenze mediche
 * e il monitoraggio dei ricoveri attivi nei reparti.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class AssenzaMedicoPostgresDAO implements AssenzaMedicoDAO {
    private Connection conn;

    public AssenzaMedicoPostgresDAO() {
        this.conn = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public boolean inserisciAssenza(String codMedico, LocalDate dataInizio, LocalDate dataFine) {
        // Chiama la procedura inserisci_assenza nel database
        String query = "CALL inserisci_assenza(?, ?, ?)";

        try (CallableStatement cstmt = conn.prepareCall(query)) {
            cstmt.setString(1, codMedico);
            cstmt.setDate(2, Date.valueOf(dataInizio));
            cstmt.setDate(3, Date.valueOf(dataFine));
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int contaRicoveriAttivi(int idReparto) {
        // Chiama la funzione SQL con SELECT invece di CALL
        String query = "SELECT conta_ricoveri_attivi(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idReparto);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}