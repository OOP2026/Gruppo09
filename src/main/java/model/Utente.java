package model;

public class Utente {
    private String username;
    private String password;

    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Mostra le info base dell'utente, sovrascritto dalle sottoclassi
    public void mostraInfo() {
        System.out.println("Utente: " + username);
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}