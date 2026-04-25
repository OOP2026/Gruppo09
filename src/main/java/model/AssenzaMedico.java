package model;

import java.time.LocalDate;

public class AssenzaMedico {

    LocalDate dataInizio;
    LocalDate dataFine;
    Medico medico;

    public AssenzaMedico(Medico medico, LocalDate dataInizio, LocalDate dataFine) {
        this.medico = medico;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public void getTurniScoperti() {
        System.out.println("Turni scoperti per il medico: " + medico.username);
        System.out.println("Periodo di assenza: dal " + dataInizio + " al " + dataFine);
        // logica per individuare turni e prestazioni senza copertura nel periodo indicato
    }

    public void mostraInfo() {
        System.out.println("Assenza del medico: " + medico.username +
                " | Dal: " + dataInizio + " al: " + dataFine);
    }
}