package model;

import java.util.ArrayList;

public class Reparto {

    int ID_reparto;
    String nome;
    ArrayList<Stanza> stanze;
    public ArrayList<Medico> medici; // Lista dei medici afferenti al reparto

    public Reparto(int ID_reparto, String nome) {
        this.ID_reparto = ID_reparto;
        this.nome = nome;
        this.stanze = new ArrayList<>();
        this.medici = new ArrayList<>(); // Inizializzazione della lista medici
    }

    // Metodo per associare un medico al reparto garantendo la coerenza bidirezionale
    public void aggiungiMedico(Medico medico) {
        if (medico != null && !this.medici.contains(medico)) {
            this.medici.add(medico);
            medico.reparto = this; // Imposta automaticamente il reparto nel medico
        }
    }

    public void cercaLettiLiberi() {
        System.out.println("Ricerca letti liberi nel reparto: " + nome);
        // logica per scorrere le stanze e restituire i letti attualmente disponibili
    }

    public void mostraInfo() {
        System.out.println("Reparto ID: " + ID_reparto + " | Nome: " + nome + " | Medici assegnati: " + medici.size());
    }
}