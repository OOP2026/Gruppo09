package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Ricovero {

    private int ID_ricovero;
    private LocalDate dataInizio;
    private LocalTime oraInizio;
    private LocalDate dataDimissionePrevista;
    private LocalTime oraDimissionePrevista;
    private LocalDate dataDimissioneEffettiva;
    private LocalTime oraDimissioneEffettiva;
    private Paziente paziente;
    private Letto letto;

    public Ricovero(int ID_ricovero, Paziente paziente, Letto letto,
                    LocalDate dataInizio, LocalTime oraInizio,
                    LocalDate dataDimissionePrevista, LocalTime oraDimissionePrevista) {
        this.ID_ricovero = ID_ricovero;
        this.paziente = paziente;
        this.letto = letto;
        this.dataInizio = dataInizio;
        this.oraInizio = oraInizio;
        this.dataDimissionePrevista = dataDimissionePrevista;
        this.oraDimissionePrevista = oraDimissionePrevista;
        this.dataDimissioneEffettiva = null;
        this.oraDimissioneEffettiva = null;
    }

    public boolean checkSovrapposizione() {
        System.out.println("DB Query: Verifica sovrapposizione temporale per il letto ID: " + letto.getID_letto());
        return false;
        // Interroga il database per verificare se l'intervallo temporale del ricovero si sovrappone a prenotazioni preesistenti per lo stesso letto
    }

    public boolean inCorso() {
        System.out.println("Verifica stato ricovero per il paziente: " + paziente.getNome() + " " + paziente.getCognome());
        return dataDimissioneEffettiva == null;
        // Determina se il ricovero è attualmente attivo verificando la mancata compilazione della data di dimissione effettiva
    }

    public void mostraInfo() {
        System.out.println("Ricovero ID: " + ID_ricovero +
                " | Paziente: " + paziente.getNome() + " " + paziente.getCognome() +
                " | Letto ID: " + letto.getID_letto() +
                " | Dal: " + dataInizio + " al: " + dataDimissionePrevista);
    }

    //Getter & Setter

    public int getID_ricovero() {
        return ID_ricovero;
    }

    public void setID_ricovero(int ID_ricovero) {
        this.ID_ricovero = ID_ricovero;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalTime getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(LocalTime oraInizio) {
        this.oraInizio = oraInizio;
    }

    public LocalDate getDataDimissionePrevista() {
        return dataDimissionePrevista;
    }

    public void setDataDimissionePrevista(LocalDate dataDimissionePrevista) {
        this.dataDimissionePrevista = dataDimissionePrevista;
    }

    public LocalTime getOraDimissionePrevista() {
        return oraDimissionePrevista;
    }

    public void setOraDimissionePrevista(LocalTime oraDimissionePrevista) {
        this.oraDimissionePrevista = oraDimissionePrevista;
    }

    public LocalDate getDataDimissioneEffettiva() {
        return dataDimissioneEffettiva;
    }

    public void setDataDimissioneEffettiva(LocalDate dataDimissioneEffettiva) {
        this.dataDimissioneEffettiva = dataDimissioneEffettiva;
    }

    public LocalTime getOraDimissioneEffettiva() {
        return oraDimissioneEffettiva;
    }

    public void setOraDimissioneEffettiva(LocalTime oraDimissioneEffettiva) {
        this.oraDimissioneEffettiva = oraDimissioneEffettiva;
    }

    public Paziente getPaziente() {
        return paziente;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }

    public Letto getLetto() {
        return letto;
    }

    public void setLetto(Letto letto) {
        this.letto = letto;
    }
}