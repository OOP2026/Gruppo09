package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {

    // Istanza unica della classe (Pattern Singleton)
    private static ConnessioneDatabase instance;

    // Oggetto che gestisce la connessione fisica al database
    private Connection connection;

    // Parametri di configurazione per l'accesso a PostgreSQL
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "dbenryjar";

    // Costruttore privato: impedisce la creazione di nuove istanze da fuori tramite "new"
    private ConnessioneDatabase() {
        try {
            // Carica in memoria il driver JDBC per PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Tenta di stabilire la connessione con i parametri indicati
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("[DB] Connessione a PostgreSQL stabilita con successo!");

        } catch (ClassNotFoundException e) {
            System.err.println("[DB ERRORE] Driver PostgreSQL non trovato.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("[DB ERRORE] Credenziali errate o database spento.");
            e.printStackTrace();
        }
    }

    // Punto di accesso pubblico per ottenere l'unica istanza della connessione
    public static ConnessioneDatabase getInstance() {
        // Se non è ancora stata creata una connessione, la inizializza
        if (instance == null) {
            instance = new ConnessioneDatabase();
        } else {
            try {
                // Se esiste già ma è stata chiusa, la riapre
                if (instance.getConnection() == null || instance.getConnection().isClosed()) {
                    instance = new ConnessioneDatabase();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    // Metodo getter per estrarre l'oggetto Connection necessario a eseguire le query nei DAO
    public Connection getConnection() {
        return connection;
    }
}