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

    public List<Letto> getLettiDisponibili() {
        System.out.println("DB Query: Recupero letti non occupati nella stanza numero: " + numero);
        return new ArrayList<>();
        // Logica per interrogare il database, filtrare i letti associati alla stanza corrente e restituire l'elenco di quelli attualmente liberi
    }

    public void mostraInfo() {
        System.out.println("Stanza numero: " + numero + " | Capienza: " + capienza);
    }

    //Getter & Setter


    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public List<Letto> getLetti() {
        return letti;
    }

    public void setLetti(List<Letto> letti) {
        this.letti = letti;
    }
}
