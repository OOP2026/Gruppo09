package dao;

import model.Utente;

/**
 * Interfaccia DAO per l'autenticazione e il recupero degli utenti del sistema.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public interface UtenteDAO {

    /**
     * Controlla le credenziali e restituisce il ruolo dell'utente.
     *
     * @param username nome utente inserito
     * @param password password inserita
     * @return ruolo dell'utente ("admin", "medico") oppure "errore" se le credenziali non sono valide
     */
    String verificaLogin(String username, String password);

    /**
     * Recupera l'oggetto utente completo partendo dallo username.
     * Restituisce un'istanza di Admin o Medico in base al tipo utente nel database.
     *
     * @param username nome utente da cercare
     * @return oggetto Admin o Medico corrispondente, null se non trovato
     */
    Utente getUtenteByUsername(String username);
}