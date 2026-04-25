package model;

public class Medico extends Utente {

    String matricola;

    public Medico(String username, String password, String matricola) {
        super(username, password);
        this.matricola = matricola;
    }

    public void agendaGiornaliera() {
        System.out.println("Agenda giornaliera del medico: " + username);
        // logica per restituire le prestazioni programmate per oggi
    }

    public void agendaSettimanale() {
        System.out.println("Agenda settimanale del medico: " + username);
        // logica per restituire le prestazioni della settimana in corso
    }

    public void registraPrestazione() {
        System.out.println("Registrazione prestazione avviata dal medico: " + username);
        // logica per verificare che la prestazione ricada in un turno
        // e che non si sovrapponga ad altre prestazioni dello stesso medico
    }

    public boolean disponibilita() {
        System.out.println("Verifica disponibilità del medico: " + username);
        // logica per verificare se il medico è libero in una fascia oraria
        return true;
    }

    public void mostraInfo() {
        System.out.println("Medico: " + username + " | Matricola: " + matricola);
    }
}