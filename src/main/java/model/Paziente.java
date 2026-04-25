package model;

public class Paziente {

    String codiceFiscale;
    String nome;
    String cognome;

    public Paziente(String codiceFiscale, String nome, String cognome) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
    }

    public void getInfoBase() {
        System.out.println("Paziente: " + nome + " " + cognome +
                " | Codice Fiscale: " + codiceFiscale);
    }

    public void mostraInfo() {
        System.out.println("Nome: " + nome + " | Cognome: " + cognome +
                " | CF: " + codiceFiscale);
    }
}