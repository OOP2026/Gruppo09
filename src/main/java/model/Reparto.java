package model;

import java.util.ArrayList;
import java.util.List;

public class Reparto {

   private int ID_reparto;
   private String nome;
   private List<Stanza> stanze;
   private List<Medico> medici; // Lista dei medici afferenti al reparto

    public Reparto(int ID_reparto, String nome) {
        this.ID_reparto = ID_reparto;
        this.nome = nome;
        this.stanze = new ArrayList<>();
        this.medici = new ArrayList<>(); // Inizializzazione della lista medici
    }

    public void aggiungiMedico(Medico medico) {
        if (medico != null && !this.medici.contains(medico)) {
            this.medici.add(medico);
            medico.setReparto(this); // Imposta automaticamente il riferimento del reparto nel medico tramite il suo metodo setter
        }
    }

    public List<Letto> cercaLettiLiberi() {
        System.out.println("DB Query: Ricerca letti liberi nel reparto: " + nome);
        return new ArrayList<>();
        // Logica per interrogare il database, scorrere le stanze associate al reparto e restituire la lista dei letti attualmente disponibili
    }

    public void mostraInfo() {
        System.out.println("Reparto ID: " + ID_reparto + " | Nome: " + nome + " | Medici assegnati: " + medici.size());
    }

    //Getter & Setter


    public int getID_reparto() {
        return ID_reparto;
    }

    public void setID_reparto(int ID_reparto) {
        this.ID_reparto = ID_reparto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Stanza> getStanze() {
        return stanze;
    }

    public void setStanze(List<Stanza> stanze) {
        this.stanze = stanze;
    }

    public List<Medico> getMedici() {
        return medici;
    }

    public void setMedici(List<Medico> medici) {
        this.medici = medici;
    }
}