package dao;

import model.Prestazione;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PrestazioneDAO {
    // Recupera le prestazioni del giorno corrente per un medico
    List<Prestazione> getAgendaGiornaliera(String matricolaMedico);

    // Recupera le prestazioni dei prossimi 7 giorni per un medico
    List<Prestazione> getAgendaSettimanale(String matricolaMedico);

    // Registra una nuova prestazione dopo i controlli di turno e sovrapposizione
    String registraPrestazione(String matricolaMedico, int idRicovero, String tipo,
                               LocalDate data, LocalTime oraInizio, LocalTime oraFine, String esito);

    // Aggiorna l'esito di una prestazione già registrata
    boolean aggiornaEsito(int idPrestazione, String nuovoEsito);
}