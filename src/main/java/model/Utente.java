package model;

/**
 * Rappresenta un utente generico del sistema ospedaliero.
 * È la superclasse di Admin e Medico, contiene le credenziali di accesso.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class Utente {
    private String username;
    private String password;

    /**
     * Costruisce un utente con le credenziali fornite.
     *
     * @param username nome utente univoco per l'accesso al sistema
     * @param password password associata all'account
     */
    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Stampa le informazioni base dell'utente.
     * Viene sovrascritto dalle sottoclassi Admin e Medico.
     */
    public void mostraInfo() {
        System.out.println("Utente: " + username);
    }

    /**
     * @return username dell'utente
     */
    public String getUsername() { return username; }

    /**
     * @param username nuovo username da impostare
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * @return password dell'utente
     */
    public String getPassword() { return password; }

    /**
     * @param password nuova password da impostare
     */
    public void setPassword(String password) { this.password = password; }
}