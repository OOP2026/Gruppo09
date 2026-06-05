package dao;

import model.Utente;

public interface UtenteDAO {
    // Controlla le credenziali e restituisce il ruolo dell'utente
    String verificaLogin(String username, String password);

    // Recupera l'oggetto utente completo partendo dallo username
    Utente getUtenteByUsername(String username);
}