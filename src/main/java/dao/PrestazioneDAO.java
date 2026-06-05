package dao;

import model.Prestazione;
import java.time.LocalTime;
import java.util.List;

public interface PrestazioneDAO {
    // Recupera le visite e gli interventi del giorno per un medico
    List<Prestazione> getAgendaGiornaliera(String matricolaMedico);

    // Recupera le prestazioni della settimana corrente per un medico
    List<Prestazione> getAgendaSettimanale(String usernameMedico);

    // Registra una nuova prestazione medica associata a un ricovero
    String registraPrestazione(String usernameMedico, int idRicovero, String tipo, LocalTime oraInizio, LocalTime oraFine, String esito);
}