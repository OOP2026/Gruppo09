package model;

public class Paziente {

    private String codiceFiscale;
    private String nome;
    private String cognome;

    public Paziente(String codiceFiscale, String nome, String cognome) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getInfoBase() {
        return nome + " " + cognome + " - " + codiceFiscale;
        // Restituisce una stringa formattata contenente i dati identificativi essenziali del paziente
    }

    public void mostraInfo() {
        System.out.println("Nome: " + nome + " | Cognome: " + cognome +
                " | CF: " + codiceFiscale);
    }

    //Getter & Setter

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}