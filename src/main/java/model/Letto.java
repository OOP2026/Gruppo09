package model;

/**
 * Rappresenta un posto letto all'interno di una stanza ospedaliera.
 * Il codice del letto è univoco nell'intero ospedale.
 * Lo stato di occupazione viene calcolato a runtime dal DAO
 * in base ai ricoveri attivi presenti nel database.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class Letto {
    private int idLetto;
    private boolean occupato;

    /**
     * Costruisce un letto con il codice fornito.
     * Lo stato iniziale è libero.
     *
     * @param idLetto codice univoco del letto nell'intero ospedale
     */
    public Letto(int idLetto) {
        this.idLetto = idLetto;
        this.occupato = false;
    }

    /**
     * Stampa le informazioni del letto con il suo stato attuale.
     */
    public void mostraInfo() {
        System.out.println("Letto ID: " + idLetto +
                " | Stato: " + (occupato ? "OCCUPATO" : "LIBERO"));
    }

    /** @return codice univoco del letto */
    public int getIdLetto() { return idLetto; }

    /** @param idLetto nuovo codice da impostare */
    public void setIdLetto(int idLetto) { this.idLetto = idLetto; }

    /** @return true se il letto è occupato, false se è libero */
    public boolean isOccupato() { return occupato; }

    /** @param occupato nuovo stato di occupazione da impostare */
    public void setOccupato(boolean occupato) { this.occupato = occupato; }
}