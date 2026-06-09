package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un reparto della struttura ospedaliera.
 * Contiene le stanze e i medici che vi afferiscono.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class Reparto {
    private int idReparto;
    private String nome;
    private List<Stanza> stanze;
    private List<Medico> medici;

    /**
     * Costruisce un reparto con l'identificativo e il nome forniti.
     *
     * @param idReparto identificativo univoco del reparto
     * @param nome      nome del reparto
     */
    public Reparto(int idReparto, String nome) {
        this.idReparto = idReparto;
        this.nome = nome;
        this.stanze = new ArrayList<>();
        this.medici = new ArrayList<>();
    }

    /**
     * Aggiunge un medico al reparto e aggiorna il riferimento bidirezionale
     * impostando questo reparto come reparto di afferenza del medico.
     *
     * @param medico medico da aggiungere al reparto
     */
    public void aggiungiMedico(Medico medico) {
        if (medico != null && !this.medici.contains(medico)) {
            this.medici.add(medico);
            medico.setReparto(this);
        }
    }

    /**
     * Aggiunge una stanza al reparto.
     *
     * @param stanza stanza da aggiungere
     */
    public void aggiungiStanza(Stanza stanza) {
        if (stanza != null && !this.stanze.contains(stanza)) {
            this.stanze.add(stanza);
        }
    }

    /**
     * Scorre tutte le stanze del reparto e raccoglie i letti non occupati.
     *
     * @return lista dei letti attualmente liberi nel reparto
     */
    public List<Letto> cercaLettiLiberi() {
        List<Letto> lettiLiberi = new ArrayList<>();
        for (Stanza stanza : stanze) {
            lettiLiberi.addAll(stanza.getLettiDisponibili());
        }
        return lettiLiberi;
    }

    /**
     * Stampa le informazioni del reparto.
     */
    public void mostraInfo() {
        System.out.println("Reparto ID: " + idReparto +
                " | Nome: " + nome +
                " | Medici: " + medici.size() +
                " | Stanze: " + stanze.size());
    }

    /** @return identificativo del reparto */
    public int getIdReparto() { return idReparto; }

    /** @param idReparto nuovo identificativo da impostare */
    public void setIdReparto(int idReparto) { this.idReparto = idReparto; }

    /** @return nome del reparto */
    public String getNome() { return nome; }

    /** @param nome nuovo nome da impostare */
    public void setNome(String nome) { this.nome = nome; }

    /** @return lista delle stanze del reparto */
    public List<Stanza> getStanze() { return stanze; }

    /** @param stanze nuova lista di stanze da impostare */
    public void setStanze(List<Stanza> stanze) { this.stanze = stanze; }

    /** @return lista dei medici del reparto */
    public List<Medico> getMedici() { return medici; }

    /** @param medici nuova lista di medici da impostare */
    public void setMedici(List<Medico> medici) { this.medici = medici; }
}