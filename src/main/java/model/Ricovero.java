package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Rappresenta il ricovero di un paziente in un letto specifico
 * per un determinato periodo di tempo.
 * Contiene sia le date di dimissione previste che quelle effettive.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
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

    /**
     * Costruisce un ricovero con le informazioni fornite.
     * La dimissione effettiva viene inizializzata a null e compilata al momento della dimissione.
     *
     * @param idRicovero              identificativo univoco del ricovero
     * @param paziente                paziente ricoverato
     * @param letto                   letto assegnato al paziente
     * @param dataInizio              data di inizio del ricovero
     * @param oraInizio               ora di inizio del ricovero
     * @param dataDimissionePrevista  data di dimissione pianificata
     * @param oraDimissionePrevista   ora di dimissione pianificata
     */
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

    /**
     * Controlla in memoria se il paziente non è ancora stato dimesso.
     *
     * @return true se il ricovero è ancora attivo, false se il paziente è stato dimesso
     */
    public boolean inCorso() {
        return dataDimissioneEffettiva == null;
    }

    /**
     * Controlla se la dimissione prevista cade nella data indicata.
     *
     * @param data data da confrontare con la dimissione prevista
     * @return true se la dimissione prevista coincide con la data, false altrimenti
     */
    public boolean inScadenzaIl(LocalDate data) {
        return dataDimissionePrevista != null && dataDimissionePrevista.equals(data);
    }

    /**
     * Stampa le informazioni del ricovero.
     */
    public void mostraInfo() {
        System.out.println("Ricovero ID: " + idRicovero +
                " | Paziente: " + paziente.getNome() + " " + paziente.getCognome() +
                " | Letto ID: " + letto.getIdLetto() +
                " | Dal: " + dataInizio +
                " | Dimissione prevista: " + dataDimissionePrevista);
    }

    /** @return identificativo del ricovero */
    public int getIdRicovero() { return idRicovero; }

    /** @param idRicovero nuovo identificativo da impostare */
    public void setIdRicovero(int idRicovero) { this.idRicovero = idRicovero; }

    /** @return data di inizio del ricovero */
    public LocalDate getDataInizio() { return dataInizio; }

    /** @param dataInizio nuova data di inizio da impostare */
    public void setDataInizio(LocalDate dataInizio) { this.dataInizio = dataInizio; }

    /** @return ora di inizio del ricovero */
    public LocalTime getOraInizio() { return oraInizio; }

    /** @param oraInizio nuova ora di inizio da impostare */
    public void setOraInizio(LocalTime oraInizio) { this.oraInizio = oraInizio; }

    /** @return data di dimissione prevista */
    public LocalDate getDataDimissionePrevista() { return dataDimissionePrevista; }

    /** @param d nuova data di dimissione prevista da impostare */
    public void setDataDimissionePrevista(LocalDate d) { this.dataDimissionePrevista = d; }

    /** @return ora di dimissione prevista */
    public LocalTime getOraDimissionePrevista() { return oraDimissionePrevista; }

    /** @param t nuova ora di dimissione prevista da impostare */
    public void setOraDimissionePrevista(LocalTime t) { this.oraDimissionePrevista = t; }

    /** @return data di dimissione effettiva, null se il paziente non è ancora dimesso */
    public LocalDate getDataDimissioneEffettiva() { return dataDimissioneEffettiva; }

    /** @param d nuova data di dimissione effettiva da impostare */
    public void setDataDimissioneEffettiva(LocalDate d) { this.dataDimissioneEffettiva = d; }

    /** @return ora di dimissione effettiva, null se il paziente non è ancora dimesso */
    public LocalTime getOraDimissioneEffettiva() { return oraDimissioneEffettiva; }

    /** @param t nuova ora di dimissione effettiva da impostare */
    public void setOraDimissioneEffettiva(LocalTime t) { this.oraDimissioneEffettiva = t; }

    /** @return paziente ricoverato */
    public Paziente getPaziente() { return paziente; }

    /** @param paziente nuovo paziente da impostare */
    public void setPaziente(Paziente paziente) { this.paziente = paziente; }

    /** @return letto assegnato al ricovero */
    public Letto getLetto() { return letto; }

    /** @param letto nuovo letto da impostare */
    public void setLetto(Letto letto) { this.letto = letto; }
}