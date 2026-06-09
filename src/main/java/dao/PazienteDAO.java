package dao;

import model.Paziente;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaccia DAO per l'accesso ai dati dei pazienti.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public interface PazienteDAO {

    /**
     * Inserisce un nuovo paziente o aggiorna i dati se il codice fiscale esiste già.
     *
     * @param paziente oggetto Paziente con i dati da salvare
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    boolean inserisciPaziente(Paziente paziente);

    /**
     * Recupera l'elenco di tutti i pazienti registrati nel sistema.
     *
     * @return lista di tutti i pazienti
     */
    List<Paziente> getAllPazienti();

    /**
     * Recupera i pazienti con dimissione prevista nella data indicata
     * che non siano ancora stati dimessi.
     *
     * @param data data per cui cercare le dimissioni previste
     * @return lista dei pazienti in scadenza nella data indicata
     */
    List<Paziente> getPazientiInScadenza(LocalDate data);
}