package dao;

import model.Utente;

public interface UtenteDAO {

    // Verifica se le credenziali sono corrette e restituisce il tipo di utente ('ADMIN' o 'MEDICO')
    String verificaLogin(String username, String password);

    // Recupera i dati completi di un utente partendo dal suo username
    Utente getUtenteByUsername(String username);
}