package model;

public class Admin extends Utente {

    public Admin(String username, String password) {
        super(username, password);
    }

    public void gestisciPazienti() {
        System.out.println("Gestione pazienti avviata dall'amministratore: " + username);
        // logica di inserimento e modifica anagrafica pazienti
    }

    public void gestisciRicoveri() {
        System.out.println("Gestione ricoveri avviata dall'amministratore: " + username);
        // logica di registrazione ricoveri con controllo sovrapposizione letti
    }

    public void elencoSostituzioni() {
        System.out.println("Ricerca medici sostitutivi avviata dall'amministratore: " + username);
        // logica di ricerca medici dello stesso reparto senza turni sovrapposti
    }

    public void mostraInfo() {
        System.out.println("Amministratore: " + username);
    }
}
