
package model;
import java.time.LocalTime;

public class Prestazione {

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

    public boolean checkSovrapposizione(Prestazione altra) {
        System.out.println("Controllo sovrapposizione per il medico: " + medico.getUsername());
        return oraInizio.isBefore(altra.getOraFine()) && oraFine.isAfter(altra.getOraInizio());
        // Logica algoritmica per verificare se l'intervallo orario della prestazione corrente si sovrappone a quello di un'altra prestazione fornita
    }

    public boolean modificaEsito(String nuovoEsito) {
        this.esito = nuovoEsito;
        System.out.println("DB Update: Aggiornamento esito per la prestazione di tipo: " + tipo + " | Nuovo Esito: " + nuovoEsito);
        return true;
        // Riceve il nuovo testo dell'esito e persiste la modifica all'interno del database, restituendo la conferma di avvenuto aggiornamento
    }

    public void mostraInfo() {
        System.out.println("Prestazione: " + tipo +
                " | Dalle: " + oraInizio + " alle: " + oraFine +
                " | Medico: " + medico.getUsername() +
                " | Esito: " + (esito != null ? esito : "non ancora compilato"));
    }

    //Getter & Setter

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalTime getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(LocalTime oraInizio) {
        this.oraInizio = oraInizio;
    }

    public LocalTime getOraFine() {
        return oraFine;
    }

    public void setOraFine(LocalTime oraFine) {
        this.oraFine = oraFine;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Ricovero getRicovero() {
        return ricovero;
    }

    public void setRicovero(Ricovero ricovero) {
        this.ricovero = ricovero;
    }
}