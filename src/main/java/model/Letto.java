package model;

public class Letto {

    private int ID_letto;
    private boolean occupato;

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

    //Getter & Setter

    public int getID_letto() {
        return ID_letto;
    }

    public void setID_letto(int ID_letto) {
        this.ID_letto = ID_letto;
    }

    public boolean isOccupato() {
        return occupato;
    }

    public void setOccupato(boolean occupato) {
        this.occupato = occupato;
    }
}
