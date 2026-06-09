package dao;

import model.Ricovero;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaccia DAO per l'accesso ai dati dei ricoveri ospedalieri.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public interface RicoveroDAO {

    /**
     * Verifica se il letto è già occupato in un intervallo di tempo sovrapposto
     * a quello del ricovero passato come parametro.
     *
     * @param ricovero ricovero da verificare
     * @return true se esiste una sovrapposizione, false se il letto è disponibile
     */
    boolean checkSovrapposizione(Ricovero ricovero);

    /**
     * Salva un nuovo ricovero nel database tramite la procedura inserisci_ricovero.
     *
     * @param ricovero oggetto Ricovero con i dati da salvare
     * @return true se il ricovero è stato salvato, false altrimenti
     */
    boolean inserisciRicovero(Ricovero ricovero);

    /**
     * Recupera i ricoveri attivi formattati come stringhe per i menu a tendina della GUI.
     *
     * @return lista di stringhe nel formato "ID | Nome Cognome (CF)"
     */
    List<String> getRicoveriAttiviPerComboBox();

    /**
     * Recupera i ricoveri con dimissione prevista nella data indicata
     * che non siano ancora stati dimessi.
     *
     * @param data data per cui cercare le dimissioni previste
     * @return lista dei ricoveri in scadenza nella data indicata
     */
    List<Ricovero> getRicoveriInScadenza(LocalDate data);
}