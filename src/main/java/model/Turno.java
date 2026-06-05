package model;

import java.time.LocalTime;

public class Turno {

    private int idTurno;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private String giornoSettimana;
    private Medico medico;

    public Turno(int idTurno, LocalTime oraInizio, LocalTime oraFine,
                 String giornoSettimana, Medico medico) {
        this.idTurno = idTurno;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.giornoSettimana = giornoSettimana;
        this.medico = medico;
    }

    // Verifica algoritmica in memoria per stabilire se un orario rientra nella fascia del turno
    public boolean copreFascia(LocalTime ora) {
        return !ora.isBefore(oraInizio) && !ora.isAfter(oraFine);
    }

    public void mostraInfo() {
        System.out.println("Turno ID: " + idTurno + " | Giorno: " + giornoSettimana +
                " | Dalle: " + oraInizio + " alle: " + oraFine +
                " | Medico: " + medico.getUsername());
    }

    // Getter & Setter
    public int getIdTurno() { return idTurno; }
    public void setIdTurno(int idTurno) { this.idTurno = idTurno; }
    public LocalTime getOraInizio() { return oraInizio; }
    public void setOraInizio(LocalTime oraInizio) { this.oraInizio = oraInizio; }
    public LocalTime getOraFine() { return oraFine; }
    public void setOraFine(LocalTime oraFine) { this.oraFine = oraFine; }
    public String getGiornoSettimana() { return giornoSettimana; }
    public void setGiornoSettimana(String giornoSettimana) { this.giornoSettimana = giornoSettimana; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
}