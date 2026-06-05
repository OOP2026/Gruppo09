package dao;

import model.Ricovero;

import java.util.List;

public interface RicoveroDAO {
    // Verifica se il letto è già impegnato in quelle date
    boolean checkSovrapposizione(Ricovero ricovero);

    // Salva il ricovero definitivo nel database
    boolean inserisciRicovero(Ricovero ricovero);

    List<String> getRicoveriAttiviPerComboBox();
}