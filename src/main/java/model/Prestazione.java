
package model;
import java.time.LocalTime;

public class Prestazione {

    String tipo;
    LocalTime oraInizio;
    LocalTime oraFine;
    String esito;
    Medico medico;
    Ricovero ricovero;

    public Prestazione(String tipo, LocalTime oraInizio, LocalTime oraFine,
                       Medico medico, Ricovero ricovero) {
        this.tipo = tipo;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.esito = null;
        this.medico = medico;
        this.ricovero = ricovero;
    }

    public boolean checkSovrapposizione(Prestazione altra) {
        System.out.println("Controllo sovrapposizione per il medico: " + medico.username);
        return oraInizio.isBefore(altra.oraFine) && oraFine.isAfter(altra.oraInizio);
    }

    public void modificaEsito(String nuovoEsito) {
        this.esito = nuovoEsito;
        System.out.println("Esito aggiornato per la prestazione di tipo: " + tipo +
                " | Esito: " + nuovoEsito);
    }

    public void mostraInfo() {
        System.out.println("Prestazione: " + tipo +
                " | Dalle: " + oraInizio + " alle: " + oraFine +
                " | Medico: " + medico.username +
                " | Esito: " + (esito != null ? esito : "non ancora compilato"));
    }
}