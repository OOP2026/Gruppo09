package dao;

import model.Ricovero;
import java.util.List;

public interface RicoveroDAO {
    // Verifica se un letto è già occupato in un intervallo di tempo
    boolean checkSovrapposizione(Ricovero ricovero);

    // Salva un nuovo ricovero nel database
    boolean inserisciRicovero(Ricovero ricovero);

    // Recupera i ricoveri correnti attivi per i menu della GUI
    List<String> getRicoveriAttiviPerComboBox();
}