package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Ricovero {

    int ID_ricovero;
    LocalDate dataInizio;
    LocalTime oraInizio;
    LocalDate dataDimissionePrevista;
    LocalTime oraDimissionePrevista;
    LocalDate dataDimissioneEffettiva;
    LocalTime oraDimissioneEffettiva;
    Paziente paziente;
    Letto letto;

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
        System.out.println("Controllo sovrapposizione per il letto ID: " + letto.ID_letto);
        return false;
    }

    public boolean inCorso() {
        System.out.println("Verifica ricovero in corso per il paziente: " +
                paziente.nome + " " + paziente.cognome);
        return dataDimissioneEffettiva == null;
    }

    public void mostraInfo() {
        System.out.println("Ricovero ID: " + ID_ricovero +
                " | Paziente: " + paziente.nome + " " + paziente.cognome +
                " | Letto ID: " + letto.ID_letto +
                " | Dal: " + dataInizio + " al: " + dataDimissionePrevista);
    }
}