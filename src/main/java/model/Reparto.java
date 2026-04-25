package model;

import java.util.ArrayList;

public class Reparto {

    int ID_reparto;
    String nome;
    ArrayList<Stanza> stanze;

    public Reparto(int ID_reparto, String nome) {
        this.ID_reparto = ID_reparto;
        this.nome = nome;
        this.stanze = new ArrayList<>();
    }

    public void cercaLettiLiberi() {
        System.out.println("Ricerca letti liberi nel reparto: " + nome);
        // logica per scorrere le stanze e restituire i letti attualmente disponibili
    }

    public void mostraInfo() {
        System.out.println("Reparto ID: " + ID_reparto + " | Nome: " + nome);
    }
}