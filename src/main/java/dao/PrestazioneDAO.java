package dao;

import model.Prestazione;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Interfaccia DAO per l'accesso ai dati delle prestazioni mediche.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public interface PrestazioneDAO {

    /**
     * Recupera le prestazioni del giorno corrente per il medico indicato.
     *
     * @param matricolaMedico matricola del medico
     * @return lista delle prestazioni di oggi
     */
    List<Prestazione> getAgendaGiornaliera(String matricolaMedico);

    /**
     * Recupera le prestazioni dei prossimi 7 giorni per il medico indicato.
     *
     * @param matricolaMedico matricola del medico
     * @return lista delle prestazioni settimanali
     */
    List<Prestazione> getAgendaSettimanale(String matricolaMedico);

    /**
     * Registra una nuova prestazione dopo i controlli di turno e sovrapposizione.
     *
     * @param matricolaMedico matricola del medico che esegue la prestazione
     * @param idRicovero      identificativo del ricovero associato
     * @param tipo            tipologia della prestazione
     * @param data            data della prestazione
     * @param oraInizio       ora di inizio
     * @param oraFine         ora di fine
     * @param esito           esito della prestazione, può essere vuoto
     * @return "OK" se la registrazione è avvenuta, messaggio di errore altrimenti
     */
    String registraPrestazione(String matricolaMedico, int idRicovero, String tipo,
                               LocalDate data, LocalTime oraInizio, LocalTime oraFine, String esito);

    /**
     * Aggiorna l'esito di una prestazione già registrata
     * tramite la procedura aggiorna_esito_prestazione nel database.
     *
     * @param idPrestazione identificativo della prestazione da aggiornare
     * @param nuovoEsito    testo del nuovo esito
     * @return true se l'aggiornamento è avvenuto, false altrimenti
     */
    boolean aggiornaEsito(int idPrestazione, String nuovoEsito);
}