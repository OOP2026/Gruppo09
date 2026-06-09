package model;

/**
 * Rappresenta un paziente registrato nel sistema ospedaliero.
 * Contiene i dati anagrafici necessari per la gestione dei ricoveri.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class Paziente {
    private String codiceFiscale;
    private String nome;
    private String cognome;

    /**
     * Costruisce un paziente con i dati anagrafici forniti.
     *
     * @param codiceFiscale codice fiscale univoco del paziente
     * @param nome          nome del paziente
     * @param cognome       cognome del paziente
     */
    public Paziente(String codiceFiscale, String nome, String cognome) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
    }

    /**
     * Restituisce una stringa riassuntiva del paziente
     * utile per le liste e le ComboBox della GUI.
     *
     * @return stringa nel formato "Nome Cognome - CodiceFiscale"
     */
    public String getInfoBase() {
        return nome + " " + cognome + " - " + codiceFiscale;
    }

    /**
     * Stampa le informazioni anagrafiche del paziente.
     */
    public void mostraInfo() {
        System.out.println("Nome: " + nome +
                " | Cognome: " + cognome +
                " | CF: " + codiceFiscale);
    }

    /** @return codice fiscale del paziente */
    public String getCodiceFiscale() { return codiceFiscale; }

    /** @param codiceFiscale nuovo codice fiscale da impostare */
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }

    /** @return nome del paziente */
    public String getNome() { return nome; }

    /** @param nome nuovo nome da impostare */
    public void setNome(String nome) { this.nome = nome; }

    /** @return cognome del paziente */
    public String getCognome() { return cognome; }

    /** @param cognome nuovo cognome da impostare */
    public void setCognome(String cognome) { this.cognome = cognome; }
}