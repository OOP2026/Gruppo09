package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Medico extends Utente {

    private String matricola;
    private Reparto reparto; // Riferimento al reparto a cui afferisce il medico

    public Medico(String username, String password, String matricola, Reparto reparto) {
        super(username, password);
        this.matricola = matricola;
        this.reparto = reparto;
    }

    public List<Prestazione> agendaGiornaliera() {
        System.out.println("DB Query: Recupero prestazioni odierne per la matricola: " + matricola);
        return new ArrayList<>();
        // logica per restituire le prestazioni programmate per oggi
    }


    public List<Prestazione> agendaSettimanale() {
        System.out.println("DB Query: Recupero prestazioni settimanali per la matricola: " + matricola);
        return new ArrayList<>();

        // logica per restituire le prestazioni della settimana in corso
    }

    public boolean registraPrestazione(Prestazione nuovaPrestazione) {
        System.out.println("DB Inserimento: Verifica vincoli e registrazione prestazione in corso...");
        return true;

        // Riceve la prestazione da convalidare e salvare nel DB
        // La logica SQL verificherà la copertura del turno e l'assenza di sovrapposizioni
    }



    public boolean disponibilita(LocalDate data, LocalTime inizio, LocalTime fine) {
        System.out.println("DB Query: Verifica impegni per il medico " + getUsername() + " in data " + data);
        return true;

        // Verifica la reperibilità del medico in una specifica finestra temporale
    }

    @Override
    public void mostraInfo() {
        String nomeReparto = (reparto != null) ? reparto.getNome() : "Nessun reparto";
        System.out.println("Medico: " + getUsername() + " | Matricola: " + matricola + " | Reparto: " + nomeReparto);
    }

    //Getter & Setter

    public String getMatricola() {
        return matricola;
    }

    public Reparto getReparto() {
        return reparto;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public void setReparto(Reparto reparto) {
        this.reparto = reparto;
    }
}