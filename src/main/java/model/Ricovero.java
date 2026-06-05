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

    // Verifica la sovrapposizione del letto interpellando il rispettivo DAO
    public boolean checkSovrapposizione(dao.RicoveroDAO ricoveroDao) {
        return ricoveroDao.checkSovrapposizione(this);
    }

    // Controlla in memoria se il ricovero è ancora attivo (mancata dimissione)
    public boolean inCorso() {
        return dataDimissioneEffettiva == null;
    }

    public void mostraInfo() {
        System.out.println("Ricovero ID: " + idRicovero +
                " | Paziente: " + paziente.getNome() + " " + paziente.getCognome() +
                " | Letto ID: " + letto.getIdLetto() +
                " | Dal: " + dataInizio + " al: " + dataDimissionePrevista);
    }

    // Getter & Setter
    public int getIdRicovero() { return idRicovero; }
    public void setIdRicovero(int idRicovero) { this.idRicovero = idRicovero; }
    public LocalDate getDataInizio() { return dataInizio; }
    public void setDataInizio(LocalDate dataInizio) { this.dataInizio = dataInizio; }
    public LocalTime getOraInizio() { return oraInizio; }
    public void setOraInizio(LocalTime oraInizio) { this.oraInizio = oraInizio; }
    public LocalDate getDataDimissionePrevista() { return dataDimissionePrevista; }
    public void setDataDimissionePrevista(LocalDate dataDimissionePrevista) { this.dataDimissionePrevista = dataDimissionePrevista; }
    public LocalTime getOraDimissionePrevista() { return oraDimissionePrevista; }
    public void setOraDimissionePrevista(LocalTime oraDimissionePrevista) { this.oraDimissionePrevista = oraDimissionePrevista; }
    public LocalDate getDataDimissioneEffettiva() { return dataDimissioneEffettiva; }
    public void setDataDimissioneEffettiva(LocalDate dataDimissioneEffettiva) { this.dataDimissioneEffettiva = dataDimissioneEffettiva; }
    public LocalTime getOraDimissioneEffettiva() { return oraDimissioneEffettiva; }
    public void setOraDimissioneEffettiva(LocalTime oraDimissioneEffettiva) { this.oraDimissioneEffettiva = oraDimissioneEffettiva; }
    public Paziente getPaziente() { return paziente; }
    public void setPaziente(Paziente paziente) { this.paziente = paziente; }
    public Letto getLetto() { return letto; }
    public void setLetto(Letto letto) { this.letto = letto; }
}