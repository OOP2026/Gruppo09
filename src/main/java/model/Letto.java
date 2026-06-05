package model;

public class Letto {
    private int idLetto;     // Mappato su codiceletto del DB
    private boolean occupato; //calcolato a runtime in base ai ricoveri attivi

    public Letto(int idLetto) {
        this.idLetto = idLetto;
        this.occupato = false;
    }

    public void mostraInfo() {
        System.out.println("Letto ID: " + idLetto + " | Stato: " + (occupato ? "OCCUPATO" : "LIBERO"));
    }

    // Getter e Setter
    public int getIdLetto() { return idLetto; }
    public void setIdLetto(int idLetto) { this.idLetto = idLetto; }
    public boolean isOccupato() { return occupato; }
    public void setOccupato(boolean occupato) { this.occupato = occupato; }
}