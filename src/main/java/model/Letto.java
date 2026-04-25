package model;

public class Letto {

    int ID_letto;
    boolean occupato;

    public Letto(int ID_letto) {
        this.ID_letto = ID_letto;
        this.occupato = false;
    }

    public void mostraInfo() {
        if (occupato) {
            System.out.println("Letto ID: " + ID_letto + " | Stato: OCCUPATO");
        } else {
            System.out.println("Letto ID: " + ID_letto + " | Stato: LIBERO");
        }
    }
}
