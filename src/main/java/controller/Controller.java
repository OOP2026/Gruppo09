package controller;

import model.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private Utente utenteLoggato;

    // LOGIN
    public String login(String username, String password) {
        if (username.equals("admin1") && password.equals("pass123")) {
            utenteLoggato = new Admin(username, password);
            return "admin";
            // Autentica l'utente amministratore inizializzandone l'istanza con le credenziali fornite
        }
        if (username.equals("mario.rossi") && password.equals("pass456")) {
            Reparto repartoTest = new Reparto(1, "Cardiologia");
            utenteLoggato = new Medico(username, password, "MAT001", repartoTest);
            return "medico";
            // Istanzia il profilo medico associandolo al rispettivo reparto per soddisfare i vincoli del costruttore
        }
        return "errore";
        // Restituisce la stringa di notifica in caso di mancata corrispondenza delle credenziali
    }

    // LOGOUT
    public void logout() {
        utenteLoggato = null;
    }

    // METODI ADMIN
    public boolean gestisciPazienti(Paziente paziente) {
        if (utenteLoggato instanceof Admin) {
            return ((Admin) utenteLoggato).gestisciPazienti(paziente);
        }
        return false;
        // Inoltra l'oggetto paziente al modello per eseguirne la persistenza o la modifica dei dati anagrafici
    }

    public boolean gestisciRicoveri(Ricovero ricovero) {
        if (utenteLoggato instanceof Admin) {
            return ((Admin) utenteLoggato).gestisciRicoveri(ricovero);
        }
        return false;
        // Trasmette l'istanza del ricovero compilata nella GUI per la convalida dei posti letto e il salvataggio
    }

    public List<Medico> elencoSostituzioni(AssenzaMedico assenza) {
        if (utenteLoggato instanceof Admin) {
            return ((Admin) utenteLoggato).elencoSostituzioni(assenza);
        }
        return new ArrayList<>();
        // Interroga i modelli per estrarre la lista dei medici idonei alla copertura dei turni scoperti
    }

    // METODI MEDICO
    public List<Prestazione> agendaGiornaliera() {
        if (utenteLoggato instanceof Medico) {
            return ((Medico) utenteLoggato).agendaGiornaliera();
        }
        return new ArrayList<>();
        // Intercetta la richiesta della GUI e interroga l'agenda del medico per ottenere le prestazioni odierne
    }

    public List<Prestazione> agendaSettimanale() {
        if (utenteLoggato instanceof Medico) {
            return ((Medico) utenteLoggato).agendaSettimanale();
        }
        return new ArrayList<>();
        // Estrae la pianificazione complessiva delle prestazioni del medico relative alla settimana in corso
    }

    public boolean registraPrestazione(Prestazione nuovaPrestazione) {
        if (utenteLoggato instanceof Medico) {
            return ((Medico) utenteLoggato).registraPrestazione(nuovaPrestazione);
        }
        return false;
        // Invia la prestazione generata dall'interfaccia grafica per convalidarne i vincoli orari nel modello
    }

    public boolean disponibilita(Medico medico, LocalDate data, LocalTime inizio, LocalTime fine) {
        return medico.disponibilita(data, inizio, fine);
        // Controlla lo stato di occupazione di un medico specifico in una determinata finestra temporale
    }
}