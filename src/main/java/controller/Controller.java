package controller;

import dao.*;
import implementazioneDao.*;
import model.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Coordinatore centrale del sistema ospedaliero.
 * Gestisce la sessione utente e instrada le richieste della GUI
 * verso i DAO e il Model, seguendo il pattern architetturale BCE+DAO.
 * Viene istanziato una volta sola all'avvio e passato a tutte le finestre.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class Controller {

    private Utente utenteLoggato;

    // I DAO sono istanziati una volta sola nel costruttore e riutilizzati
    private final UtenteDAO utenteDao;
    private final PazienteDAO pazienteDao;
    private final RicoveroDAO ricoveroDao;
    private final MedicoDAO medicoDao;
    private final PrestazioneDAO prestazioneDao;
    private final LettoDAO lettoDao;
    private final AssenzaMedicoDAO assenzaDao;

    /**
     * Inizializza il Controller istanziando tutti i DAO necessari.
     * Ogni DAO viene creato una sola volta e riutilizzato per tutta
     * la durata della sessione.
     */
    public Controller() {
        this.utenteDao      = new UtentePostgresDAO();
        this.pazienteDao    = new PazientePostgresDAO();
        this.ricoveroDao    = new RicoveroPostgresDAO();
        this.medicoDao      = new MedicoPostgresDAO();
        this.prestazioneDao = new PrestazionePostgresDAO();
        this.lettoDao       = new LettoPostgresDAO();
        this.assenzaDao     = new AssenzaMedicoPostgresDAO();
    }

    // -------------------------------------------------------------------------
    // AUTENTICAZIONE
    // -------------------------------------------------------------------------

    /**
     * Verifica le credenziali e avvia la sessione dell'utente.
     * Carica l'oggetto utente completo in memoria se il login ha successo.
     *
     * @param username nome utente inserito
     * @param password password inserita
     * @return ruolo dell'utente ("admin", "medico") oppure "errore" se le credenziali non sono valide
     */
    public String login(String username, String password) {
        String ruolo = utenteDao.verificaLogin(username, password);
        if (!ruolo.equals("errore")) {
            this.utenteLoggato = utenteDao.getUtenteByUsername(username);
        }
        return ruolo;
    }

    /**
     * Termina la sessione corrente azzerando l'utente loggato.
     */
    public void logout() {
        this.utenteLoggato = null;
    }

    /**
     * @return utente attualmente loggato nel sistema
     */
    public Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    // -------------------------------------------------------------------------
    // FUNZIONALITÀ AMMINISTRATORE
    // -------------------------------------------------------------------------

    /**
     * Inserisce o aggiorna l'anagrafica di un paziente.
     * Accessibile solo agli amministratori.
     *
     * @param paziente oggetto Paziente con i dati da salvare
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    public boolean gestisciPazienti(Paziente paziente) {
        if (!(utenteLoggato instanceof Admin)) return false;
        return pazienteDao.inserisciPaziente(paziente);
    }

    /**
     * Verifica che il letto non sia già occupato nel periodo indicato
     * e, se libero, registra il ricovero.
     * Accessibile solo agli amministratori.
     *
     * @param ricovero oggetto Ricovero con i dati da salvare
     * @return true se il ricovero è stato registrato, false se il letto è già occupato
     */
    public boolean gestisciRicoveri(Ricovero ricovero) {
        if (!(utenteLoggato instanceof Admin)) return false;
        if (ricoveroDao.checkSovrapposizione(ricovero)) return false;
        return ricoveroDao.inserisciRicovero(ricovero);
    }

    /**
     * Restituisce i medici sostitutivi disponibili per il periodo indicato.
     * I candidati vengono cercati tra i colleghi dello stesso reparto
     * del medico assente che non hanno turni o prestazioni sovrapposte.
     * Accessibile solo agli amministratori.
     *
     * @param matricolaAssente matricola del medico assente
     * @param inizio           data di inizio del periodo di assenza
     * @param fine             data di fine del periodo di assenza
     * @return lista dei medici disponibili come sostituti
     */
    public List<Medico> calcolaSostituti(String matricolaAssente, LocalDate inizio, LocalDate fine) {
        if (!(utenteLoggato instanceof Admin)) return new ArrayList<>();
        return medicoDao.getSostitutiIdonei(matricolaAssente, inizio, fine);
    }

    /**
     * Restituisce i pazienti con dimissione prevista nella giornata odierna.
     * Accessibile solo agli amministratori.
     *
     * @return lista dei pazienti in scadenza oggi
     */
    public List<Paziente> getPazientiInScadenzaOggi() {
        if (!(utenteLoggato instanceof Admin)) return new ArrayList<>();
        return pazienteDao.getPazientiInScadenza(LocalDate.now());
    }

    /**
     * Restituisce i pazienti con dimissione prevista in una data specifica.
     * Accessibile solo agli amministratori.
     *
     * @param data data per cui cercare le dimissioni previste
     * @return lista dei pazienti in scadenza nella data indicata
     */
    public List<Paziente> getPazientiInScadenza(LocalDate data) {
        if (!(utenteLoggato instanceof Admin)) return new ArrayList<>();
        return pazienteDao.getPazientiInScadenza(data);
    }

    /**
     * Restituisce i letti di un reparto con il loro stato attuale di occupazione.
     * Accessibile solo agli amministratori.
     *
     * @param idReparto identificativo del reparto
     * @return lista dei letti con stato OCCUPATO o LIBERO
     */
    public List<Letto> getLettiPerReparto(int idReparto) {
        if (!(utenteLoggato instanceof Admin)) return new ArrayList<>();
        return lettoDao.getLettiPerReparto(idReparto);
    }

    /**
     * Registra un periodo di assenza per malattia di un medico.
     * Accessibile solo agli amministratori.
     *
     * @param codMedico  matricola del medico assente
     * @param dataInizio data di inizio dell'assenza
     * @param dataFine   data di fine dell'assenza
     * @return true se l'assenza è stata registrata, false altrimenti
     */
    public boolean inserisciAssenza(String codMedico, LocalDate dataInizio, LocalDate dataFine) {
        if (!(utenteLoggato instanceof Admin)) return false;
        return assenzaDao.inserisciAssenza(codMedico, dataInizio, dataFine);
    }

    /**
     * Restituisce il numero di ricoveri attivi in un reparto
     * tramite la funzione SQL conta_ricoveri_attivi.
     * Accessibile solo agli amministratori.
     *
     * @param idReparto identificativo del reparto
     * @return numero di ricoveri attualmente attivi nel reparto
     */
    public int contaRicoveriAttivi(int idReparto) {
        if (!(utenteLoggato instanceof Admin)) return 0;
        return assenzaDao.contaRicoveriAttivi(idReparto);
    }

    // -------------------------------------------------------------------------
    // FUNZIONALITÀ MEDICO
    // -------------------------------------------------------------------------

    /**
     * Restituisce le prestazioni del medico loggato per la giornata odierna.
     * Accessibile solo ai medici.
     *
     * @return lista delle prestazioni di oggi del medico loggato
     */
    public List<Prestazione> agendaGiornaliera() {
        if (!(utenteLoggato instanceof Medico)) return new ArrayList<>();
        return prestazioneDao.getAgendaGiornaliera(((Medico) utenteLoggato).getMatricola());
    }

    /**
     * Restituisce le prestazioni del medico loggato per i prossimi 7 giorni.
     * Accessibile solo ai medici.
     *
     * @return lista delle prestazioni settimanali del medico loggato
     */
    public List<Prestazione> agendaSettimanale() {
        if (!(utenteLoggato instanceof Medico)) return new ArrayList<>();
        return prestazioneDao.getAgendaSettimanale(((Medico) utenteLoggato).getMatricola());
    }

    /**
     * Registra una nuova prestazione per il medico loggato.
     * La data viene impostata automaticamente a oggi.
     * I controlli di turno e sovrapposizione sono delegati al DAO.
     * Accessibile solo ai medici.
     *
     * @param idRicovero identificativo del ricovero associato
     * @param tipo       tipologia della prestazione
     * @param oraInizio  ora di inizio della prestazione
     * @param oraFine    ora di fine della prestazione
     * @param esito      esito della prestazione, può essere vuoto
     * @return "OK" se la registrazione è avvenuta, messaggio di errore altrimenti
     */
    public String registraPrestazione(int idRicovero, String tipo,
                                      LocalTime oraInizio, LocalTime oraFine, String esito) {
        if (!(utenteLoggato instanceof Medico)) return "Errore: operazione non consentita.";
        String matricola = ((Medico) utenteLoggato).getMatricola();
        return prestazioneDao.registraPrestazione(matricola, idRicovero, tipo,
                LocalDate.now(), oraInizio, oraFine, esito);
    }

    /**
     * Aggiorna l'esito di una prestazione già registrata.
     * Accessibile solo ai medici.
     *
     * @param idPrestazione identificativo della prestazione da aggiornare
     * @param nuovoEsito    testo del nuovo esito
     * @return true se l'aggiornamento è avvenuto, false altrimenti
     */
    public boolean aggiornaEsitoPrestazione(int idPrestazione, String nuovoEsito) {
        if (!(utenteLoggato instanceof Medico)) return false;
        return prestazioneDao.aggiornaEsito(idPrestazione, nuovoEsito);
    }

    // -------------------------------------------------------------------------
    // METODI DI LETTURA PER POPOLARE I COMPONENTI DELLA GUI
    // -------------------------------------------------------------------------

    /**
     * @return lista di tutti i pazienti registrati nel sistema
     */
    public List<Paziente> recuperaTuttiPazienti() {
        return pazienteDao.getAllPazienti();
    }

    /**
     * @return lista di tutti i letti con il loro stato attuale
     */
    public List<Letto> recuperaTuttiLetti() {
        return lettoDao.getAllLetti();
    }

    /**
     * @return lista di tutti i medici registrati nel sistema
     */
    public List<Medico> recuperaTuttiMedici() {
        return medicoDao.getAllMedici();
    }

    /**
     * @return lista dei ricoveri attivi formattati per le ComboBox della GUI
     */
    public List<String> recuperaRicoveriPerComboBox() {
        return ricoveroDao.getRicoveriAttiviPerComboBox();
    }
}