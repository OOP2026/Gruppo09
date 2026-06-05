package model;

import java.util.ArrayList;
import java.util.List;

public class Reparto {
    private int idReparto;
    private String nome;
    private List<Stanza> stanze;
    private List<Medico> medici;

    public Reparto(int idReparto, String nome) {
        this.idReparto = idReparto;
        this.nome = nome;
        this.stanze = new ArrayList<>();
        this.medici = new ArrayList<>();
    }

    // Aggiunge un medico al reparto e aggiorna il riferimento bidirezionale
    public void aggiungiMedico(Medico medico) {
        if (medico != null && !this.medici.contains(medico)) {
            this.medici.add(medico);
            medico.setReparto(this);
        }
    }

    // Aggiunge una stanza al reparto
    public void aggiungiStanza(Stanza stanza) {
        if (stanza != null && !this.stanze.contains(stanza)) {
            this.stanze.add(stanza);
        }
    }

    // Scorre tutte le stanze del reparto e raccoglie i letti non occupati
    public List<Letto> cercaLettiLiberi() {
        List<Letto> lettiLiberi = new ArrayList<>();
        for (Stanza stanza : stanze) {
            lettiLiberi.addAll(stanza.getLettiDisponibili());
        }
        return lettiLiberi;
    }

    public void mostraInfo() {
        System.out.println("Reparto ID: " + idReparto +
                " | Nome: " + nome +
                " | Medici: " + medici.size() +
                " | Stanze: " + stanze.size());
    }

    public int getIdReparto() { return idReparto; }
    public void setIdReparto(int idReparto) { this.idReparto = idReparto; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Stanza> getStanze() { return stanze; }
    public void setStanze(List<Stanza> stanze) { this.stanze = stanze; }
    public List<Medico> getMedici() { return medici; }
    public void setMedici(List<Medico> medici) { this.medici = medici; }
}