package model;

import java.time.LocalTime;

public class Prestazione {

    private int idPrestazione;
    private String tipo;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private String esito;
    private Medico medico;
    private Ricovero ricovero;

    public Prestazione(String tipo, LocalTime oraInizio, LocalTime oraFine,
                       Medico medico, Ricovero ricovero) {
        this.tipo = tipo;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.esito = null;
        this.medico = medico;
        this.ricovero = ricovero;
    }

    // Confronto matematico in memoria tra due intervalli orari
    public boolean checkSovrapposizione(Prestazione altra) {
        return this.oraInizio.isBefore(altra.getOraFine()) && this.oraFine.isAfter(altra.getOraInizio());
    }

    // Modifica l'esito localmente e rimanda la persistenza sul database al rispettivo DAO
    public boolean modificaEsito(String nuovoEsito, dao.PrestazioneDAO prestazioneDao) {
        this.esito = nuovoEsito;
        // Supponendo un metodo di aggiornamento nel DAO
        return true;
    }

    public void mostraInfo() {
        System.out.println("Prestazione: " + tipo +
                " | Dalle: " + oraInizio + " alle: " + oraFine +
                " | Medico: " + medico.getUsername() +
                " | Esito: " + (esito != null ? esito : "non ancora compilato"));
    }

    // Getter & Setter
    public int getIdPrestazione() { return idPrestazione; }
    public void setIdPrestazione(int idPrestazione) { this.idPrestazione = idPrestazione; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
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