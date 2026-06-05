package dao;

import model.Paziente;
import java.time.LocalDate;
import java.util.List;

public interface PazienteDAO {
    // Inserisce o aggiorna l'anagrafica di un paziente
    boolean inserisciPaziente(Paziente paziente);

    // Recupera l'elenco di tutti i pazienti registrati
    List<Paziente> getAllPazienti();

    // Recupera i pazienti con dimissione prevista nella data indicata
    List<Paziente> getPazientiInScadenza(LocalDate data);
}