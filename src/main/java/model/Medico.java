package model;

import dao.PrestazioneDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Medico extends Utente {
    private String matricola;
    private Reparto reparto;

    public Medico(String username, String password, String matricola, Reparto reparto) {
        super(username, password);
        this.matricola = matricola;
        this.reparto = reparto;
    }

    // Interroga il DB tramite il passaggio dell'interfaccia DAO
    public List<Prestazione> agendaGiornaliera(PrestazioneDAO prestazioneDao) {
        return prestazioneDao.getAgendaGiornaliera(this.matricola);
    }

    // Interroga il DB tramite il passaggio dell'interfaccia DAO
    public List<Prestazione> agendaSettimanale(PrestazioneDAO prestazioneDao) {
        return prestazioneDao.getAgendaSettimanale(getUsername());
    }

    // Delega la registrazione della prestazione al DAO competente
    public String registraPrestazione(int idRicovero, String tipo, LocalTime oraInizio, LocalTime oraFine, String esito, PrestazioneDAO prestazioneDao) {
        return prestazioneDao.registraPrestazione(getUsername(), idRicovero, tipo, oraInizio, oraFine, esito);
    }

    // Verifica la disponibilità oraria del medico sfruttando il metodo DAO validato
    public boolean disponibilita(LocalDate data, LocalTime inizio, LocalTime fine, dao.MedicoDAO medicoDao) {
        return medicoDao.verificaDisponibilita(this.matricola, data, inizio, fine);
    }

    @Override
    public void mostraInfo() {
        String nomeReparto = (reparto != null) ? reparto.getNome() : "Nessun reparto";
        System.out.println("Medico: " + getUsername() + " | Matricola: " + matricola + " | Reparto: " + nomeReparto);
    }

    // Getter e Setter
    public String getMatricola() { return matricola; }
    public void setMatricola(String matricola) { this.matricola = matricola; }
    public Reparto getReparto() { return reparto; }
    public void setReparto(Reparto reparto) { this.reparto = reparto; }
}