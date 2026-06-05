package model;

import java.util.ArrayList;
import java.util.List;

public class Stanza {
    private int numero;
    private int capienza;
    private List<Letto> letti;

    public Stanza(int numero, int capienza) {
        this.numero = numero;
        this.capienza = capienza;
        this.letti = new ArrayList<>();
    }

    // Aggiunge un letto alla stanza
    public void aggiungiLetto(Letto letto) {
        if (letto != null && !this.letti.contains(letto)) {
            this.letti.add(letto);
        }
    }

    // Restituisce i letti della stanza che risultano liberi in memoria
    public List<Letto> getLettiDisponibili() {
        List<Letto> disponibili = new ArrayList<>();
        for (Letto letto : letti) {
            if (!letto.isOccupato()) {
                disponibili.add(letto);
            }
        }
        return disponibili;
    }

    public void mostraInfo() {
        System.out.println("Stanza numero: " + numero +
                " | Capienza: " + capienza +
                " | Letti registrati: " + letti.size());
    }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public int getCapienza() { return capienza; }
    public void setCapienza(int capienza) { this.capienza = capienza; }
    public List<Letto> getLetti() { return letti; }
    public void setLetti(List<Letto> letti) { this.letti = letti; }
}