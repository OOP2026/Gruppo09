package model;

/**
 * Rappresenta un amministratore del sistema ospedaliero.
 * Estende Utente e ha accesso alle funzionalità di gestione
 * di pazienti, ricoveri e assenze del personale medico.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class Admin extends Utente {

    /**
     * Costruisce un amministratore con le credenziali fornite.
     *
     * @param username nome utente univoco per l'accesso al sistema
     * @param password password associata all'account
     */
    public Admin(String username, String password) {
        super(username, password);
    }

    /**
     * Stampa le informazioni dell'amministratore.
     */
    @Override
    public void mostraInfo() {
        System.out.println("Amministratore: " + getUsername());
    }
}