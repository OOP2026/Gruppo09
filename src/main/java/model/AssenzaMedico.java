package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AssenzaMedico {

    private LocalDate dataInizio;
    private LocalDate dataFine;
    private Medico medico;

    public AssenzaMedico(Medico medico, LocalDate dataInizio, LocalDate dataFine) {
        this.medico = medico;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public List<Turno> getTurniScoperti() {
        System.out.println("DB Query: Individuazione turni scoperti per il medico: " + medico.getUsername());
        System.out.println("Periodo di assenza: dal " + dataInizio + " al " + dataFine);
        return new ArrayList<>();
        // Logica per interrogare il database e individuare i turni e le prestazioni rimasti senza copertura nel periodo indicato
    }

    public void mostraInfo() {
        System.out.println("Assenza del medico: " + medico.getUsername() +
                " | Dal: " + dataInizio + " al: " + dataFine);
    }

    //Getter & Setter


    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}