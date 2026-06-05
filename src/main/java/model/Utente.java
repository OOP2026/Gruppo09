package model;

public class Utente {
    private String username;
    private String password;

    // Costruttore base
    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void mostraInfo() {
        System.out.println("Utente: " + username);
    }

    // Getter e Setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}