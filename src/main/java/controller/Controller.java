package controller;

import model.Admin;
import model.Medico;
import model.Utente;

public class Controller {

    private Utente utenteLoggato;

    // LOGIN
    public String login(String username, String password) {
        if (username.equals("admin1") && password.equals("pass123")) {
            utenteLoggato = new Admin(username, password);
            return "admin";
        }
        if (username.equals("mario.rossi") && password.equals("pass456")) {
            utenteLoggato = new Medico(username, password, "MAT001");
            return "medico";
        }
        return "errore";
    }

    // LOGOUT
    public void logout() {
        utenteLoggato = null;
    }

    // METODI ADMIN
    public void gestisciPazienti() {
        ((Admin) utenteLoggato).gestisciPazienti();
    }

    public void gestisciRicoveri() {
        ((Admin) utenteLoggato).gestisciRicoveri();
    }

    public void elencoSostituzioni() {
        ((Admin) utenteLoggato).elencoSostituzioni();
    }

    // METODI MEDICO
    public void agendaGiornaliera() {
        ((Medico) utenteLoggato).agendaGiornaliera();
    }

    public void agendaSettimanale() {
        ((Medico) utenteLoggato).agendaSettimanale();
    }

    public void registraPrestazione() {
        ((Medico) utenteLoggato).registraPrestazione();
    }

    public void disponibilita() {
        ((Medico) utenteLoggato).disponibilita();
    }
}