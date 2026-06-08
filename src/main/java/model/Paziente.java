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

    // Restituisce una stringa riassuntiva del paziente
    public String getInfoBase() {
        return nome + " " + cognome + " - " + codiceFiscale;
    }

    public void mostraInfo() {
        System.out.println("Nome: " + nome +
                " | Cognome: " + cognome +
                " | CF: " + codiceFiscale);
    }

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
}