package dao;

import model.Medico;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MedicoDAO {
    // Recupera l'elenco completo dei medici
    List<Medico> getAllMedici();

    // Estrae i possibili sostituti per un medico assente in un determinato periodo
    List<Medico> getSostitutiIdonei(String matricolaAssente, LocalDate inizio, LocalDate fine);

    // Verifica che il medico abbia un turno attivo e nessuna sovrapposizione in quella fascia
    boolean verificaDisponibilita(String matricolaMedico, LocalDate data, LocalTime oraInizio, LocalTime oraFine);
}