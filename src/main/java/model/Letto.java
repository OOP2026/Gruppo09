package model;

public class Letto {
    // Corrisponde a codiceletto nel DB, univoco nell'intero ospedale
    private int idLetto;
    // Calcolato a runtime dal Controller in base ai ricoveri attivi
    private boolean occupato;

    public Letto(int idLetto) {
        this.idLetto = idLetto;
        this.occupato = false;
    }

    public void mostraInfo() {
        System.out.println("Letto ID: " + idLetto +
                " | Stato: " + (occupato ? "OCCUPATO" : "LIBERO"));
    }

    public int getIdLetto() { return idLetto; }
    public void setIdLetto(int idLetto) { this.idLetto = idLetto; }
    public boolean isOccupato() { return occupato; }
    public void setOccupato(boolean occupato) { this.occupato = occupato; }
}