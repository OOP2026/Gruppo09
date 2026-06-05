package model;

import java.time.LocalDate;

public class AssenzaMedico {

    private LocalDate dataInizio;
    private LocalDate dataFine;
    private Medico medico;

    public AssenzaMedico(Medico medico, LocalDate dataInizio, LocalDate dataFine) {
        this.medico = medico;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    // Controlla in memoria se una data rientra nel periodo di assenza
    public boolean copreData(LocalDate data) {
        return !data.isBefore(dataInizio) && !data.isAfter(dataFine);
    }

    public void mostraInfo() {
        System.out.println("Assenza medico: " + medico.getUsername() +
                " | Dal: " + dataInizio + " al: " + dataFine);
    }

    public LocalDate getDataInizio() { return dataInizio; }
    public void setDataInizio(LocalDate dataInizio) { this.dataInizio = dataInizio; }
    public LocalDate getDataFine() { return dataFine; }
    public void setDataFine(LocalDate dataFine) { this.dataFine = dataFine; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
}