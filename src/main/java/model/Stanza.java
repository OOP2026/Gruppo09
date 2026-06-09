package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta una stanza all'interno di un reparto ospedaliero.
 * Contiene i letti assegnati e la logica per verificarne la disponibilità.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class Stanza {
    private int numero;
    private int capienza;
    private List<Letto> letti;

    /**
     * Costruisce una stanza con il numero e la capienza forniti.
     *
     * @param numero   numero identificativo della stanza nel reparto
     * @param capienza numero massimo di letti che la stanza può ospitare
     */
    public Stanza(int numero, int capienza) {
        this.numero = numero;
        this.capienza = capienza;
        this.letti = new ArrayList<>();
    }

    /**
     * Aggiunge un letto alla stanza.
     *
     * @param letto letto da aggiungere
     */
    public void aggiungiLetto(Letto letto) {
        if (letto != null && !this.letti.contains(letto)) {
            this.letti.add(letto);
        }
    }

    /**
     * Restituisce i letti della stanza che risultano liberi in memoria.
     *
     * @return lista dei letti non occupati nella stanza
     */
    public List<Letto> getLettiDisponibili() {
        List<Letto> disponibili = new ArrayList<>();
        for (Letto letto : letti) {
            if (!letto.isOccupato()) {
                disponibili.add(letto);
            }
        }
        return disponibili;
    }

    /**
     * Stampa le informazioni della stanza.
     */
    public void mostraInfo() {
        System.out.println("Stanza numero: " + numero +
                " | Capienza: " + capienza +
                " | Letti registrati: " + letti.size());
    }

    /** @return numero identificativo della stanza */
    public int getNumero() { return numero; }

    /** @param numero nuovo numero da impostare */
    public void setNumero(int numero) { this.numero = numero; }

    /** @return capienza massima della stanza */
    public int getCapienza() { return capienza; }

    /** @param capienza nuova capienza da impostare */
    public void setCapienza(int capienza) { this.capienza = capienza; }

    /** @return lista dei letti della stanza */
    public List<Letto> getLetti() { return letti; }

    /** @param letti nuova lista di letti da impostare */
    public void setLetti(List<Letto> letti) { this.letti = letti; }
}