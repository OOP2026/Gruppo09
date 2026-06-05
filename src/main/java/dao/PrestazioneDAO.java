package dao;

import model.Prestazione;

import java.time.LocalTime;
import java.util.List;

public interface PrestazioneDAO {
    List<Prestazione> getAgendaGiornaliera(String matricolaMedico);
    List<Prestazione> getAgendaSettimanale(String usernameMedico);
    String registraPrestazione(String usernameMedico, int idRicovero, String tipo, LocalTime oraInizio, LocalTime oraFine, String esito);
}