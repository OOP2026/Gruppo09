package dao;

import model.Ricovero;
import java.time.LocalDate;
import java.util.List;

public interface RicoveroDAO {
    // Verifica se il letto è già occupato nell'intervallo del nuovo ricovero
    boolean checkSovrapposizione(Ricovero ricovero);

    // Salva un nuovo ricovero nel database
    boolean inserisciRicovero(Ricovero ricovero);

    // Recupera i ricoveri attivi formattati per i menu a tendina della GUI
    List<String> getRicoveriAttiviPerComboBox();

    // Recupera i ricoveri con dimissione prevista nella data indicata
    List<Ricovero> getRicoveriInScadenza(LocalDate data);
}