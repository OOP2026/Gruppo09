package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {

    // Istanza statica per il pattern Singleton
    private static ConnessioneDatabase instance;

    // Oggetto per la gestione della connessione JDBC
    private Connection connection;

    // Parametri di configurazione del database locale
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "dbenryjar";

    // Costruttore privato per impedire istanziazioni esterne tramite new
    private ConnessioneDatabase() {
        try {
            // Caricamento del driver driver JDBC di Postgres
            Class.forName("org.postgresql.Driver");

            // Apertura della connessione fisica
            this.connection = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            System.err.println("Driver PostgreSQL non trovato nel CLASSPATH.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Impossibile connettersi al DB: verificare credenziali o stato del server.");
            e.printStackTrace();
        }
    }

    // Restituisce l'istanza unica della connessione, creandola se nulla o chiusa
    public static ConnessioneDatabase getInstance() {
        try {
            if (instance == null || instance.connection == null || instance.connection.isClosed()) {
                instance = new ConnessioneDatabase();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;
    }

    // Getter per l'oggetto Connection utilizzabile dalle classi DAO
    public Connection getConnection() {
        return connection;
    }
}