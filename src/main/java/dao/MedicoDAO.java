package dao;

import model.Medico;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MedicoDAO {
    // Recupera tutti i medici per popolare la ComboBox della scelta iniziale
    List<Medico> getAllMedici();

    // Calcola i sostituti idonei in base al reparto e alle date di assenza
    List<Medico> getSostitutiIdonei(String matricolaAssente, LocalDate inizio, LocalDate fine);

    boolean verificaDisponibilita(String matricolaMedico, LocalDate data, LocalTime oraInizio, LocalTime oraFine);
}