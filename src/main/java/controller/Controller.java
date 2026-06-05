package controller;

import dao.UtenteDAO;
import implementazioneDao.*;
import model.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private Utente utenteLoggato;
    private UtenteDAO utenteDao;

    public Controller() {
        // Inizializzazione del DAO di autenticazione tramite il costruttore
        this.utenteDao = new UtentePostgresDAO();
    }

    // --- AUTENTICAZIONE E GESTIONE SESSIONE ---

    public String login(String username, String password) {
        String ruolo = utenteDao.verificaLogin(username, password);
        if (!ruolo.equals("errore")) {
            this.utenteLoggato = utenteDao.getUtenteByUsername(username);
        }
        return ruolo;
    }

    public void logout() {
        this.utenteLoggato = null;
    }

    // --- LOGICA AMMINISTRATORE (DOWNCASTING SICURO) ---

    public boolean gestisciPazienti(Paziente paziente) {
        if (utenteLoggato instanceof Admin) {
            return ((Admin) utenteLoggato).gestisciPazienti(paziente, new PazientePostgresDAO());
        }
        return false;
    }

    public boolean gestisciRicoveri(Ricovero ricovero) {
        if (utenteLoggato instanceof Admin) {
            return ((Admin) utenteLoggato).gestisciRicoveri(ricovero, new RicoveroPostgresDAO());
        }
        return false;
    }

    public List<Medico> calcolaSostituti(String matricolaAssente, LocalDate inizio, LocalDate fine) {
        if (utenteLoggato instanceof Admin) {
            return ((Admin) utenteLoggato).elencoSostituzioni(matricolaAssente, inizio, fine, new MedicoPostgresDAO());
        }
        return new ArrayList<>();
    }

    // --- LOGICA MEDICO (DOWNCASTING SICURO) ---

    public List<Prestazione> agendaGiornaliera() {
        if (utenteLoggato instanceof Medico) {
            return ((Medico) utenteLoggato).agendaGiornaliera(new PrestazionePostgresDAO());
        }
        return new ArrayList<>();
    }

    public List<Prestazione> agendaSettimanale() {
        if (utenteLoggato instanceof Medico) {
            return ((Medico) utenteLoggato).agendaSettimanale(new PrestazionePostgresDAO());
        }
        return new ArrayList<>();
    }

    // Metodo allineato alla firma richiesta da RegistraPrestazionePanel
    public String registraPrestazione(int idRicovero, String tipo, LocalTime oraInizio, LocalTime oraFine, String esito) {
        if (utenteLoggato instanceof Medico) {
            return ((Medico) utenteLoggato).registraPrestazione(idRicovero, tipo, oraInizio, oraFine, esito, new PrestazionePostgresDAO());
        }
        return "Errore: Operazione non consentita per l'utente corrente.";
    }

    // --- METODI DI SOLA LETTURA PER POPOLARE I COMPONENTI DELLA GUI ---

    public List<Paziente> recuperaTuttiPazienti() {
        return new PazientePostgresDAO().getAllPazienti();
    }

    public List<Letto> recuperaTuttiLetti() {
        return new LettoPostgresDAO().getAllLetti();
    }

    public List<Medico> recuperaTuttiMedici() {
        return new MedicoPostgresDAO().getAllMedici();
    }

    public List<String> recuperaRicoveriPerComboBox() {
        return new RicoveroPostgresDAO().getRicoveriAttiviPerComboBox();
    }

    public boolean checkDisponibilitaMedico(String matricolaMedico, LocalDate data, LocalTime oraInizio, LocalTime oraFine) {
        return new MedicoPostgresDAO().verificaDisponibilita(matricolaMedico, data, oraInizio, oraFine);
    }
}