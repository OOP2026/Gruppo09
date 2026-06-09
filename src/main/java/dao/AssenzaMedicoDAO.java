package dao;

import java.time.LocalDate;

/**
 * Interfaccia DAO per la gestione delle assenze del personale medico
 * e per il monitoraggio dei ricoveri attivi nei reparti.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public interface AssenzaMedicoDAO {

    /**
     * Registra un periodo di assenza per malattia di un medico
     * tramite la procedura inserisci_assenza nel database.
     *
     * @param codMedico  matricola del medico assente
     * @param dataInizio data di inizio dell'assenza
     * @param dataFine   data di fine dell'assenza
     * @return true se l'assenza è stata registrata, false altrimenti
     */
    boolean inserisciAssenza(String codMedico, LocalDate dataInizio, LocalDate dataFine);

    /**
     * Conta i ricoveri attivi in un reparto tramite la funzione SQL conta_ricoveri_attivi.
     *
     * @param idReparto identificativo del reparto
     * @return numero di ricoveri attualmente attivi nel reparto
     */
    int contaRicoveriAttivi(int idReparto);
}