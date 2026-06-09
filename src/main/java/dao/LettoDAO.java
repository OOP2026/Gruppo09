package dao;

import model.Letto;
import java.util.List;

/**
 * Interfaccia DAO per l'accesso ai dati dei letti ospedalieri.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public interface LettoDAO {

    /**
     * Recupera tutti i letti della struttura con il loro stato attuale di occupazione.
     *
     * @return lista di tutti i letti con stato aggiornato
     */
    List<Letto> getAllLetti();

    /**
     * Recupera i letti di un reparto specifico con il loro stato attuale di occupazione.
     *
     * @param idReparto identificativo del reparto
     * @return lista dei letti del reparto con stato aggiornato
     */
    List<Letto> getLettiPerReparto(int idReparto);
}