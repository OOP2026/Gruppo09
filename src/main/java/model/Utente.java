package model;

import java.util.ArrayList;

public class Utente {

    String username;
    String password;

    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            System.out.println("Accesso effettuato con successo!");
            return true;
        } else {
            System.out.println("Username o password errati.");
            return false;
        }
    }

    public void mostraInfo() {
        System.out.println("Utente: " + username);
    }
}