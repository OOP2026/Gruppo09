package controller;

import dao.*;
import implementazioneDao.*;
import model.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private Utente utenteLoggato;

    // I DAO sono istanziati una volta sola nel costruttore e riutilizzati
    private final UtenteDAO utenteDao;
    private final PazienteDAO pazienteDao;
    private final RicoveroDAO ricoveroDao;
    private final MedicoDAO medicoDao;
    private final PrestazioneDAO prestazioneDao;
    private final LettoDAO lettoDao;
    private final AssenzaDAO assenzaDao;

    public Controller() {
        this.utenteDao      = new UtentePostgresDAO();
        this.pazienteDao    = new PazientePostgresDAO();
        this.ricoveroDao    = new RicoveroPostgresDAO();
        this.medicoDao      = new MedicoPostgresDAO();
        this.prestazioneDao = new PrestazionePostgresDAO();
        this.lettoDao       = new LettoPostgresDAO();
        this.assenzaDao     = new AssenzaPostgresDAO();
    }

    // -------------------------------------------------------------------------
    // AUTENTICAZIONE
    // -------------------------------------------------------------------------

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

    public Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    // -------------------------------------------------------------------------
    // FUNZIONALITÀ AMMINISTRATORE
    // -------------------------------------------------------------------------

    // Inserisce o aggiorna l'anagrafica di un paziente
    public boolean gestisciPazienti(Paziente paziente) {
        if (!(utenteLoggato instanceof Admin)) return false;
        return pazienteDao.inserisciPaziente(paziente);
    }

    // Verifica la sovrapposizione e, se libero, registra il ricovero
    public boolean gestisciRicoveri(Ricovero ricovero) {
        if (!(utenteLoggato instanceof Admin)) return false;
        if (ricoveroDao.checkSovrapposizione(ricovero)) return false;
        return ricoveroDao.inserisciRicovero(ricovero);
    }

    // Restituisce i medici sostitutivi disponibili per il periodo indicato
    public List<Medico> calcolaSostituti(String matricolaAssente, LocalDate inizio, LocalDate fine) {
        if (!(utenteLoggato instanceof Admin)) return new ArrayList<>();
        return medicoDao.getSostitutiIdonei(matricolaAssente, inizio, fine);
    }

    // Restituisce i pazienti con dimissione prevista oggi
    public List<Paziente> getPazientiInScadenzaOggi() {
        if (!(utenteLoggato instanceof Admin)) return new ArrayList<>();
        return pazienteDao.getPazientiInScadenza(LocalDate.now());
    }

    // Restituisce i pazienti con dimissione prevista in una data specifica
    public List<Paziente> getPazientiInScadenza(LocalDate data) {
        if (!(utenteLoggato instanceof Admin)) return new ArrayList<>();
        return pazienteDao.getPazientiInScadenza(data);
    }

    // Restituisce i letti di un reparto con il loro stato attuale di occupazione
    public List<Letto> getLettiPerReparto(int idReparto) {
        if (!(utenteLoggato instanceof Admin)) return new ArrayList<>();
        return lettoDao.getLettiPerReparto(idReparto);
    }

    // Registra un periodo di assenza per malattia di un medico
    public boolean inserisciAssenza(String codMedico, LocalDate dataInizio, LocalDate dataFine) {
        if (!(utenteLoggato instanceof Admin)) return false;
        return assenzaDao.inserisciAssenza(codMedico, dataInizio, dataFine);
    }

    // Restituisce il numero di ricoveri attivi in un reparto
    public int contaRicoveriAttivi(int idReparto) {
        if (!(utenteLoggato instanceof Admin)) return 0;
        return assenzaDao.contaRicoveriAttivi(idReparto);
    }

    // -------------------------------------------------------------------------
    // FUNZIONALITÀ MEDICO
    // -------------------------------------------------------------------------

    // Restituisce le prestazioni del medico loggato per la giornata odierna
    public List<Prestazione> agendaGiornaliera() {
        if (!(utenteLoggato instanceof Medico)) return new ArrayList<>();
        return prestazioneDao.getAgendaGiornaliera(((Medico) utenteLoggato).getMatricola());
    }

    // Restituisce le prestazioni del medico loggato per i prossimi 7 giorni
    public List<Prestazione> agendaSettimanale() {
        if (!(utenteLoggato instanceof Medico)) return new ArrayList<>();
        return prestazioneDao.getAgendaSettimanale(((Medico) utenteLoggato).getMatricola());
    }

    // Registra una nuova prestazione dopo i controlli di turno e sovrapposizione
    public String registraPrestazione(int idRicovero, String tipo,
                                      LocalTime oraInizio, LocalTime oraFine, String esito) {
        if (!(utenteLoggato instanceof Medico)) return "Errore: operazione non consentita.";
        String matricola = ((Medico) utenteLoggato).getMatricola();
        return prestazioneDao.registraPrestazione(matricola, idRicovero, tipo,
                LocalDate.now(), oraInizio, oraFine, esito);
    }

    // Aggiorna l'esito di una prestazione già registrata
    public boolean aggiornaEsitoPrestazione(int idPrestazione, String nuovoEsito) {
        if (!(utenteLoggato instanceof Medico)) return false;
        return prestazioneDao.aggiornaEsito(idPrestazione, nuovoEsito);
    }

    // -------------------------------------------------------------------------
    // METODI DI LETTURA PER POPOLARE I COMPONENTI DELLA GUI
    // -------------------------------------------------------------------------

    public List<Paziente> recuperaTuttiPazienti() {
        return pazienteDao.getAllPazienti();
    }

    public List<Letto> recuperaTuttiLetti() {
        return lettoDao.getAllLetti();
    }

    public List<Medico> recuperaTuttiMedici() {
        return medicoDao.getAllMedici();
    }

    public List<String> recuperaRicoveriPerComboBox() {
        return ricoveroDao.getRicoveriAttiviPerComboBox();
    }
}