package model;

import java.util.ArrayList;

public class Stanza {

    int numero;
    int capienza;
    ArrayList<Letto> letti;

    public Stanza(int numero, int capienza) {
        this.numero = numero;
        this.capienza = capienza;
        this.letti = new ArrayList<>();
    }

    public void getLettiDisponibili() {
        System.out.println("Letti disponibili nella stanza numero: " + numero);
        // logica per scorrere i letti e restituire quelli non occupati
    }

    public void mostraInfo() {
        System.out.println("Stanza numero: " + numero + " | Capienza: " + capienza);
    }
}
