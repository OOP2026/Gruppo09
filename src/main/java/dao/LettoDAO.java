package dao;

import model.Letto;
import java.util.List;

public interface LettoDAO {
    // Recupera l'elenco di tutti i letti della struttura
    List<Letto> getAllLetti();
}