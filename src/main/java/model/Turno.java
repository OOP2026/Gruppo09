package model;

import java.time.LocalTime;

/**
 * Rappresenta una fascia oraria lavorativa settimanale assegnata a un medico.
 * Contiene la logica per verificare se un orario o una fascia rientrano nel turno.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class Turno {

    private int idTurno;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private String giornoSettimana;
    private Medico medico;

    /**
     * Costruisce un turno lavorativo con le informazioni fornite.
     *
     * @param idTurno        identificativo univoco del turno
     * @param oraInizio      ora di inizio della fascia lavorativa
     * @param oraFine        ora di fine della fascia lavorativa
     * @param giornoSettimana giorno della settimana del turno
     * @param medico         medico assegnato al turno
     */
    public Turno(int idTurno, LocalTime oraInizio, LocalTime oraFine,
                 String giornoSettimana, Medico medico) {
        this.idTurno = idTurno;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.giornoSettimana = giornoSettimana;
        this.medico = medico;
    }

    /**
     * Verifica se un orario rientra nella fascia del turno.
     *
     * @param ora orario da verificare
     * @return true se l'orario è compreso nel turno, false altrimenti
     */
    public boolean copreFascia(LocalTime ora) {
        return !ora.isBefore(oraInizio) && !ora.isAfter(oraFine);
    }

    /**
     * Verifica se una fascia oraria è interamente coperta dal turno.
     *
     * @param inizio ora di inizio della fascia da verificare
     * @param fine   ora di fine della fascia da verificare
     * @return true se la fascia è interamente compresa nel turno, false altrimenti
     */
    public boolean copreFasciaCompleta(LocalTime inizio, LocalTime fine) {
        return !inizio.isBefore(oraInizio) && !fine.isAfter(oraFine);
    }

    /**
     * Stampa le informazioni del turno.
     */
    public void mostraInfo() {
        System.out.println("Turno ID: " + idTurno +
                " | Giorno: " + giornoSettimana +
                " | Dalle: " + oraInizio + " alle: " + oraFine +
                " | Medico: " + medico.getUsername());
    }

    /** @return identificativo del turno */
    public int getIdTurno() { return idTurno; }

    /** @param idTurno nuovo identificativo da impostare */
    public void setIdTurno(int idTurno) { this.idTurno = idTurno; }

    /** @return ora di inizio del turno */
    public LocalTime getOraInizio() { return oraInizio; }

    /** @param oraInizio nuova ora di inizio da impostare */
    public void setOraInizio(LocalTime oraInizio) { this.oraInizio = oraInizio; }

    /** @return ora di fine del turno */
    public LocalTime getOraFine() { return oraFine; }

    /** @param oraFine nuova ora di fine da impostare */
    public void setOraFine(LocalTime oraFine) { this.oraFine = oraFine; }

    /** @return giorno della settimana del turno */
    public String getGiornoSettimana() { return giornoSettimana; }

    /** @param g nuovo giorno della settimana da impostare */
    public void setGiornoSettimana(String g) { this.giornoSettimana = g; }

    /** @return medico assegnato al turno */
    public Medico getMedico() { return medico; }

    /** @param medico nuovo medico da impostare */
    public void setMedico(Medico medico) { this.medico = medico; }
}