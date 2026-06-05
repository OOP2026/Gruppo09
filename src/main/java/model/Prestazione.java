package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Prestazione {

    private int idPrestazione;
    private String tipo;
    private LocalDate data;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private String esito;
    private Medico medico;
    private Ricovero ricovero;

    public Prestazione(String tipo, LocalDate data, LocalTime oraInizio, LocalTime oraFine,
                       Medico medico, Ricovero ricovero) {
        this.tipo = tipo;
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.esito = null;
        this.medico = medico;
        this.ricovero = ricovero;
    }

    // Controlla in memoria se due prestazioni si sovrappongono nella stessa giornata
    public boolean checkSovrapposizione(Prestazione altra) {
        if (!this.data.equals(altra.getData())) return false;
        return this.oraInizio.isBefore(altra.getOraFine()) &&
                this.oraFine.isAfter(altra.getOraInizio());
    }

    // Aggiorna l'esito in memoria; la persistenza su DB è delegata al Controller tramite DAO
    public void modificaEsito(String nuovoEsito) {
        this.esito = nuovoEsito;
    }

    public void mostraInfo() {
        System.out.println("Prestazione: " + tipo +
                " | Data: " + data +
                " | Dalle: " + oraInizio + " alle: " + oraFine +
                " | Medico: " + medico.getUsername() +
                " | Esito: " + (esito != null ? esito : "non ancora compilato"));
    }

    public int getIdPrestazione() { return idPrestazione; }
    public void setIdPrestazione(int idPrestazione) { this.idPrestazione = idPrestazione; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public LocalTime getOraInizio() { return oraInizio; }
    public void setOraInizio(LocalTime oraInizio) { this.oraInizio = oraInizio; }
    public LocalTime getOraFine() { return oraFine; }
    public void setOraFine(LocalTime oraFine) { this.oraFine = oraFine; }
    public String getEsito() { return esito; }
    public void setEsito(String esito) { this.esito = esito; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    public Ricovero getRicovero() { return ricovero; }
    public void setRicovero(Ricovero ricovero) { this.ricovero = ricovero; }
}