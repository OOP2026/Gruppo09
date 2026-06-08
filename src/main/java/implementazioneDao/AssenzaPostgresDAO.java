package implementazioneDao;

import dao.AssenzaDAO;
import database_connection.ConnessioneDatabase;
import java.sql.*;
import java.time.LocalDate;

public class AssenzaPostgresDAO implements AssenzaDAO {
    private Connection conn;

    public AssenzaPostgresDAO() {
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
        // Chiama la funzione con SELECT invece di CALL
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