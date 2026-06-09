package model;

import java.time.LocalDate;

/**
 * Rappresenta un periodo di assenza per malattia di un medico.
 * Contiene la logica per verificare se una data rientra nel periodo di assenza.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class AssenzaMedico {

    private LocalDate dataInizio;
    private LocalDate dataFine;
    private Medico medico;

    /**
     * Costruisce un periodo di assenza per il medico indicato.
     *
     * @param medico     medico assente
     * @param dataInizio data di inizio dell'assenza
     * @param dataFine   data di fine dell'assenza
     */
    public AssenzaMedico(Medico medico, LocalDate dataInizio, LocalDate dataFine) {
        this.medico = medico;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    /**
     * Controlla in memoria se una data rientra nel periodo di assenza.
     *
     * @param data data da verificare
     * @return true se la data è compresa nel periodo di assenza, false altrimenti
     */
    public boolean copreData(LocalDate data) {
        return !data.isBefore(dataInizio) && !data.isAfter(dataFine);
    }

    /**
     * Stampa le informazioni sull'assenza del medico.
     */
    public void mostraInfo() {
        System.out.println("Assenza medico: " + medico.getUsername() +
                " | Dal: " + dataInizio + " al: " + dataFine);
    }

    /** @return data di inizio dell'assenza */
    public LocalDate getDataInizio() { return dataInizio; }

    /** @param dataInizio nuova data di inizio da impostare */
    public void setDataInizio(LocalDate dataInizio) { this.dataInizio = dataInizio; }

    /** @return data di fine dell'assenza */
    public LocalDate getDataFine() { return dataFine; }

    /** @param dataFine nuova data di fine da impostare */
    public void setDataFine(LocalDate dataFine) { this.dataFine = dataFine; }

    /** @return medico associato all'assenza */
    public Medico getMedico() { return medico; }

    /** @param medico nuovo medico da impostare */
    public void setMedico(Medico medico) { this.medico = medico; }
}