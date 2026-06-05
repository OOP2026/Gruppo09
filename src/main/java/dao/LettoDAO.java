package dao;

import model.Letto;
import java.util.List;

public interface LettoDAO {
    // Recupera tutti i letti censiti nell'ospedale per i menu a tendina
    List<Letto> getAllLetti();
}