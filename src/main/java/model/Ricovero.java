package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Ricovero {

    private int idRicovero;
    private LocalDate dataInizio;
    private LocalTime oraInizio;
    private LocalDate dataDimissionePrevista;
    private LocalTime oraDimissionePrevista;
    private LocalDate dataDimissioneEffettiva;
    private LocalTime oraDimissioneEffettiva;
    private Paziente paziente;
    private Letto letto;

    public Ricovero(int idRicovero, Paziente paziente, Letto letto,
                    LocalDate dataInizio, LocalTime oraInizio,
                    LocalDate dataDimissionePrevista, LocalTime oraDimissionePrevista) {
        this.idRicovero = idRicovero;
        this.paziente = paziente;
        this.letto = letto;
        this.dataInizio = dataInizio;
        this.oraInizio = oraInizio;
        this.dataDimissionePrevista = dataDimissionePrevista;
        this.oraDimissionePrevista = oraDimissionePrevista;
        this.dataDimissioneEffettiva = null;
        this.oraDimissioneEffettiva = null;
    }

    // Controlla in memoria se il paziente non è ancora stato dimesso
    public boolean inCorso() {
        return dataDimissioneEffettiva == null;
    }

    // Controlla se la dimissione prevista cade nella data passata come parametro
    public boolean inScadenzaIl(LocalDate data) {
        return dataDimissionePrevista != null && dataDimissionePrevista.equals(data);
    }

    public void mostraInfo() {
        System.out.println("Ricovero ID: " + idRicovero +
                " | Paziente: " + paziente.getNome() + " " + paziente.getCognome() +
                " | Letto ID: " + letto.getIdLetto() +
                " | Dal: " + dataInizio +
                " | Dimissione prevista: " + dataDimissionePrevista);
    }

    public int getIdRicovero() { return idRicovero; }
    public void setIdRicovero(int idRicovero) { this.idRicovero = idRicovero; }
    public LocalDate getDataInizio() { return dataInizio; }
    public void setDataInizio(LocalDate dataInizio) { this.dataInizio = dataInizio; }
    public LocalTime getOraInizio() { return oraInizio; }
    public void setOraInizio(LocalTime oraInizio) { this.oraInizio = oraInizio; }
    public LocalDate getDataDimissionePrevista() { return dataDimissionePrevista; }
    public void setDataDimissionePrevista(LocalDate d) { this.dataDimissionePrevista = d; }
    public LocalTime getOraDimissionePrevista() { return oraDimissionePrevista; }
    public void setOraDimissionePrevista(LocalTime t) { this.oraDimissionePrevista = t; }
    public LocalDate getDataDimissioneEffettiva() { return dataDimissioneEffettiva; }
    public void setDataDimissioneEffettiva(LocalDate d) { this.dataDimissioneEffettiva = d; }
    public LocalTime getOraDimissioneEffettiva() { return oraDimissioneEffettiva; }
    public void setOraDimissioneEffettiva(LocalTime t) { this.oraDimissioneEffettiva = t; }
    public Paziente getPaziente() { return paziente; }
    public void setPaziente(Paziente paziente) { this.paziente = paziente; }
    public Letto getLetto() { return letto; }
    public void setLetto(Letto letto) { this.letto = letto; }
}