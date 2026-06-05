package model;

public class Utente {

    // Campi per memorizzare le credenziali di accesso
    private String username;
    private String password;

    // Costruttore base per inizializzare l'utente
    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // NOTA: Il metodo login() che controllava le stringhe in memoria RAM
    // è stato rimosso. La verifica delle credenziali adesso viene eseguita
    // direttamente su PostgreSQL tramite la classe UtentePostgresDAO.

    // Metodo di utilità per stampare a console l'identificativo dell'utente
    public void mostraInfo() {
        System.out.println("Utente: " + username);
    }

    //Getter & Setter

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

