package model;

import java.util.ArrayList;
import java.util.List;

public class Reparto {
    private int idReparto; // Uniformato alle convenzioni Java (idreparto nel DB)
    private String nome;
    private List<Stanza> stanze;
    private List<Medico> medici;

    public Reparto(int idReparto, String nome) {
        this.idReparto = idReparto;
        this.nome = nome;
        this.stanze = new ArrayList<>();
        this.medici = new ArrayList<>();
    }

    // Associazione bidirezionale tra Medico e Reparto
    public void aggiungiMedico(Medico medico) {
        if (medico != null && !this.medici.contains(medico)) {
            this.medici.add(medico);
            medico.setReparto(this);
        }
    }

    // Realizza la ricerca in memoria
    public List<Letto> cercaLettiLiberi() {
        List<Letto> lettiLiberi = new ArrayList<>();
        for (Stanza stanza : stanze) {
            lettiLiberi.addAll(stanza.getLettiDisponibili());
        }
        return lettiLiberi;
    }

    public void mostraInfo() {
        System.out.println("Reparto ID: " + idReparto + " | Nome: " + nome + " | Medici assegnati: " + medici.size());
    }

    // Getter e Setter
    public int getIdReparto() { return idReparto; }
    public void setIdReparto(int idReparto) { this.idReparto = idReparto; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Stanza> getStanze() { return stanze; }
    public void setStanze(List<Stanza> stanze) { this.stanze = stanze; }
    public List<Medico> getMedici() { return medici; }
    public void setMedici(List<Medico> medici) { this.medici = medici; }
}