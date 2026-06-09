package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestisce la connessione al database PostgreSQL tramite il pattern Singleton.
 * Garantisce che esista al massimo una sola connessione attiva durante
 * tutta la durata dell'applicazione, evitando problemi di concorrenza.
 * Le credenziali sono scritte direttamente nel codice per semplicità didattica.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class ConnessioneDatabase {

    // Istanza statica per il pattern Singleton
    private static ConnessioneDatabase instance;

    // Oggetto per la gestione della connessione JDBC
    private Connection connection;

    // Parametri di configurazione del database locale
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "password";

    // Costruttore privato per impedire istanziazioni esterne tramite new
    private ConnessioneDatabase() {
        try {
            // Caricamento dinamico del driver JDBC di PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Apertura della connessione fisica al database
            this.connection = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            System.err.println("Driver PostgreSQL non trovato nel CLASSPATH.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Impossibile connettersi al DB: verificare credenziali o stato del server.");
            e.printStackTrace();
        }
    }

    /**
     * Restituisce l'istanza unica della connessione al database.
     * Se la connessione non esiste o è stata chiusa ne crea una nuova.
     *
     * @return istanza unica di ConnessioneDatabase
     */
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

    /**
     * @return oggetto Connection utilizzabile dalle classi DAO per eseguire query
     */
    public Connection getConnection() {
        return connection;
    }
}