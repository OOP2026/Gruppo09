package model;

import java.time.LocalTime;

public class Turno {

   private int ID_Turno;
   private LocalTime oraInizio;
   private LocalTime oraFine;
   private String giornoSettimana;
   private Medico medico;

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
        return !ora.isBefore(oraInizio) && !ora.isAfter(oraFine);
        // Valuta se l'orario passato come parametro è compreso all'interno della finestra temporale delimitata dall'inizio e dalla fine del turno corrente
    }

    public void mostraInfo() {
        System.out.println("Turno ID: " + ID_Turno + " | Giorno: " + giornoSettimana +
                " | Dalle: " + oraInizio + " alle: " + oraFine +
                " | Medico: " + medico.getUsername());
    }

    //Getter & Setter
    public int getID_Turno() {
        return ID_Turno;
    }

    public void setID_Turno(int ID_Turno) {
        this.ID_Turno = ID_Turno;
    }

    public LocalTime getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(LocalTime oraInizio) {
        this.oraInizio = oraInizio;
    }

    public LocalTime getOraFine() {
        return oraFine;
    }

    public void setOraFine(LocalTime oraFine) {
        this.oraFine = oraFine;
    }

    public String getGiornoSettimana() {
        return giornoSettimana;
    }

    public void setGiornoSettimana(String giornoSettimana) {
        this.giornoSettimana = giornoSettimana;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}