package model;

import java.time.LocalTime;

public class Turno {

    int ID_Turno;
    LocalTime oraInizio;
    LocalTime oraFine;
    String giornoSettimana;
    Medico medico;

    public Turno(int ID_Turno, LocalTime oraInizio, LocalTime oraFine,
                 String giornoSettimana, Medico medico) {
        this.ID_Turno = ID_Turno;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.giornoSettimana = giornoSettimana;
        this.medico = medico;
    }

    public boolean copreFascia(LocalTime ora) {
        System.out.println("Verifica se l'ora " + ora + " ricade nel turno ID: " + ID_Turno);
        // logica per verificare se l'ora passata è compresa tra oraInizio e oraFine
        return !ora.isBefore(oraInizio) && !ora.isAfter(oraFine);
    }

    public void mostraInfo() {
        System.out.println("Turno ID: " + ID_Turno + " | Giorno: " + giornoSettimana +
                " | Dalle: " + oraInizio + " alle: " + oraFine +
                " | Medico: " + medico.username);
    }
}