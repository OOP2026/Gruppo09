package dao;

import model.Paziente;
import java.util.List;

public interface PazienteDAO {

    // Gestisce sia il salvataggio che l'aggiornamento dell'anagrafica
    boolean inserisciPaziente(Paziente paziente);

    // Recupera la lista di tutti i pazienti
    List<Paziente> getAllPazienti();
}