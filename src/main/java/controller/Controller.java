package controller;

import dao.UtenteDAO;
import implementazioneDao.UtentePostgresDAO;
import implementazioneDao.PazientePostgresDAO;
import implementazioneDao.LettoPostgresDAO;
import model.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private Utente utenteLoggato;
    private UtenteDAO utenteDao; // Oggetto per comunicare con il database

    // Costruttore: inizializza il gestore degli utenti con l'implementazione Postgres
    public Controller() {
        this.utenteDao = new UtentePostgresDAO();
    }

    // ==========================================
    // SEZIONE AUTENTICAZIONE (LOGIN / LOGOUT)
    // ==========================================

    public String login(String username, String password) {
        // Interroga il database per verificare credenziali e ruolo
        String ruolo = utenteDao.verificaLogin(username, password);

        // Se le credenziali sono valide, carica l'oggetto utente reale nella sessione
        if (!ruolo.equals("errore")) {
            this.utenteLoggato = utenteDao.getUtenteByUsername(username);
        }

        // Restituisce il ruolo ("admin", "medico" o "errore") alla GUI
        return ruolo;
    }

    public void logout() {
        utenteLoggato = null;
    }

    // ==========================================
    // SEZIONE METODI AMMINISTRATORE (ADMIN)
    // ==========================================

    public boolean gestisciPazienti(Paziente paziente) {
        if (utenteLoggato instanceof Admin) {
            return ((Admin) utenteLoggato).gestisciPazienti(paziente);
        }
        return false;
    }

    public boolean gestisciRicoveri(Ricovero ricovero) {
        if (utenteLoggato instanceof Admin) {
            return ((Admin) utenteLoggato).gestisciRicoveri(ricovero);
        }
        return false;
    }

    public List<Medico> elencoSostituzioni(AssenzaMedico assenza) {
        if (utenteLoggato instanceof Admin) {
            return ((Admin) utenteLoggato).elencoSostituzioni(assenza);
        }
        return new ArrayList<>();
    }

    // ==========================================
    // METODI DI LETTURA PER SUPPORTO COSTRUTTORI GUI
    // ==========================================

    // Recupera la lista di tutti i pazienti per popolare le JComboBox nei form
    public List<Paziente> recuperaTuttiPazienti() {
        return new PazientePostgresDAO().getAllPazienti();
    }

    // Recupera la lista di tutti i letti per popolare le JComboBox nei form
    public List<Letto> recuperaTuttiLetti() {
        return new LettoPostgresDAO().getAllLetti();
    }

    // ==========================================
    // SEZIONE METODI MEDICO
    // ==========================================

    public List<Prestazione> agendaGiornaliera() {
        if (utenteLoggato instanceof Medico) {
            return ((Medico) utenteLoggato).agendaGiornaliera();
        }
        return new ArrayList<>();
    }

    public List<Prestazione> agendaSettimanale() {
        if (utenteLoggato instanceof Medico) {
            return ((Medico) utenteLoggato).agendaSettimanale();
        }
        return new ArrayList<>();
    }

    public boolean registraPrestazione(Prestazione nuovaPrestazione) {
        if (utenteLoggato instanceof Medico) {
            return ((Medico) utenteLoggato).registraPrestazione(nuovaPrestazione);
        }
        return false;
    }

    public boolean disponibilita(Medico medico, LocalDate data, LocalTime inizio, LocalTime fine) {
        return medico.disponibilita(data, inizio, fine);
    }

    // Recupera tutti i medici per la tendina dei medici assenti
    public List<Medico> recuperaTuttiMedici() {
        return new implementazioneDao.MedicoPostgresDAO().getAllMedici();
    }

    // Esegue il calcolo dei sostituti e restituisce la lista da mostrare nella JTable
    public List<Medico> calcolaSostituti(String matricolaAssente, LocalDate inizio, LocalDate fine) {
        return new implementazioneDao.MedicoPostgresDAO().getSostitutiIdonei(matricolaAssente, inizio, fine);
    }

    public List<Prestazione> recuperaAgendaGiornaliera(String matricolaMedico) {
        return new implementazioneDao.PrestazionePostgresDAO().getAgendaGiornaliera(matricolaMedico);
    }

    public List<Prestazione> recuperaAgendaSettimanale(String usernameMedico) {
        return new implementazioneDao.PrestazionePostgresDAO().getAgendaSettimanale(usernameMedico);
    }
    public String registraNuovaPrestazione(String usernameMedico, int idRicovero, String tipo, LocalTime oraInizio, LocalTime oraFine, String esito) {
        return new implementazioneDao.PrestazionePostgresDAO().registraPrestazione(usernameMedico, idRicovero, tipo, oraInizio, oraFine, esito);
    }

    public java.util.List<String> recuperaRicoveriPerComboBox() {
        return new implementazioneDao.RicoveroPostgresDAO().getRicoveriAttiviPerComboBox();
    }

    public boolean checkDisponibilitaMedico(String matricolaMedico, java.time.LocalDate data, java.time.LocalTime oraInizio, java.time.LocalTime oraFine) {
        return new implementazioneDao.MedicoPostgresDAO().verificaDisponibilita(matricolaMedico, data, oraInizio, oraFine);
    }
}

