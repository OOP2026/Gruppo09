package dao;

import model.Medico;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Interfaccia DAO per l'accesso ai dati dei medici.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public interface MedicoDAO {

    /**
     * Recupera l'elenco completo dei medici registrati nel sistema.
     *
     * @return lista di tutti i medici
     */
    List<Medico> getAllMedici();

    /**
     * Estrae i medici dello stesso reparto del medico assente
     * che sono disponibili per tutto il periodo indicato.
     *
     * @param matricolaAssente matricola del medico assente
     * @param inizio           data di inizio del periodo
     * @param fine             data di fine del periodo
     * @return lista dei medici idonei alla sostituzione
     */
    List<Medico> getSostitutiIdonei(String matricolaAssente, LocalDate inizio, LocalDate fine);

    /**
     * Verifica che il medico abbia un turno attivo nella fascia oraria indicata
     * e che non abbia prestazioni o assenze sovrapposte.
     *
     * @param matricolaMedico matricola del medico da verificare
     * @param data            data della verifica
     * @param oraInizio       ora di inizio della fascia da verificare
     * @param oraFine         ora di fine della fascia da verificare
     * @return true se il medico è disponibile, false altrimenti
     */
    boolean verificaDisponibilita(String matricolaMedico, LocalDate data, LocalTime oraInizio, LocalTime oraFine);
}