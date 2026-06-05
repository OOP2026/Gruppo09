package model;

public class Admin extends Utente {

    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public void mostraInfo() {
        System.out.println("Amministratore: " + getUsername());
    }
}