package dao;

import model.Letto;
import java.util.List;

public interface LettoDAO {
    // Recupera tutti i letti della struttura con il loro stato attuale
    List<Letto> getAllLetti();

    // Recupera i letti di un reparto specifico con il loro stato attuale
    List<Letto> getLettiPerReparto(int idReparto);
}