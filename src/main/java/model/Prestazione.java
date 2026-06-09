package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Rappresenta una prestazione medica eseguita da un medico su un paziente ricoverato.
 * Può essere una visita o un intervento chirurgico.
 * Contiene la logica per verificare sovrapposizioni temporali tra prestazioni.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class Prestazione {

    private int idPrestazione;
    private String tipo;
    private LocalDate data;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private String esito;
    private Medico medico;
    private Ricovero ricovero;

    /**
     * Costruisce una prestazione con le informazioni fornite.
     * L'esito viene inizializzato a null e compilato successivamente dal medico.
     *
     * @param tipo     tipologia della prestazione (visita o intervento)
     * @param data     data in cui viene eseguita la prestazione
     * @param oraInizio ora di inizio della prestazione
     * @param oraFine   ora di fine della prestazione
     * @param medico   medico che esegue la prestazione
     * @param ricovero ricovero a cui è associata la prestazione
     */
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

    /**
     * Controlla in memoria se questa prestazione si sovrappone con un'altra
     * nella stessa giornata.
     *
     * @param altra prestazione con cui verificare la sovrapposizione
     * @return true se le due prestazioni si sovrappongono, false altrimenti
     */
    public boolean checkSovrapposizione(Prestazione altra) {
        if (!this.data.equals(altra.getData())) return false;
        return this.oraInizio.isBefore(altra.getOraFine()) &&
                this.oraFine.isAfter(altra.getOraInizio());
    }

    /**
     * Aggiorna l'esito in memoria.
     * La persistenza sul database è delegata al Controller tramite DAO.
     *
     * @param nuovoEsito testo dell'esito da impostare
     */
    public void modificaEsito(String nuovoEsito) {
        this.esito = nuovoEsito;
    }

    /**
     * Stampa le informazioni della prestazione.
     */
    public void mostraInfo() {
        System.out.println("Prestazione: " + tipo +
                " | Data: " + data +
                " | Dalle: " + oraInizio + " alle: " + oraFine +
                " | Medico: " + medico.getUsername() +
                " | Esito: " + (esito != null ? esito : "non ancora compilato"));
    }

    /** @return identificativo della prestazione */
    public int getIdPrestazione() { return idPrestazione; }

    /** @param idPrestazione nuovo identificativo da impostare */
    public void setIdPrestazione(int idPrestazione) { this.idPrestazione = idPrestazione; }

    /** @return tipologia della prestazione */
    public String getTipo() { return tipo; }

    /** @param tipo nuova tipologia da impostare */
    public void setTipo(String tipo) { this.tipo = tipo; }

    /** @return data della prestazione */
    public LocalDate getData() { return data; }

    /** @param data nuova data da impostare */
    public void setData(LocalDate data) { this.data = data; }

    /** @return ora di inizio della prestazione */
    public LocalTime getOraInizio() { return oraInizio; }

    /** @param oraInizio nuova ora di inizio da impostare */
    public void setOraInizio(LocalTime oraInizio) { this.oraInizio = oraInizio; }

    /** @return ora di fine della prestazione */
    public LocalTime getOraFine() { return oraFine; }

    /** @param oraFine nuova ora di fine da impostare */
    public void setOraFine(LocalTime oraFine) { this.oraFine = oraFine; }

    /** @return esito della prestazione, null se non ancora compilato */
    public String getEsito() { return esito; }

    /** @param esito nuovo esito da impostare */
    public void setEsito(String esito) { this.esito = esito; }

    /** @return medico che ha eseguito la prestazione */
    public Medico getMedico() { return medico; }

    /** @param medico nuovo medico da impostare */
    public void setMedico(Medico medico) { this.medico = medico; }

    /** @return ricovero associato alla prestazione */
    public Ricovero getRicovero() { return ricovero; }

    /** @param ricovero nuovo ricovero da impostare */
    public void setRicovero(Ricovero ricovero) { this.ricovero = ricovero; }
}