package model;
import java.util.ArrayList;
import java.util.List;

public class Admin extends Utente {

    public Admin(String username, String password) {
        super(username, password);
    }

    public boolean gestisciPazienti(Paziente paziente) {
        System.out.println("DB Operazione: Inserimento/Modifica nel database del paziente: " + paziente.getNome());
        return true;
        // logica di inserimento e modifica anagrafica pazienti
        //restituisce true se sul DB l'operazione è andata a buon fine
    }

    public boolean gestisciRicoveri(Ricovero ricovero) {
        System.out.println("DB Operazione: Validazione e inserimento ricovero per il letto: " + ricovero.getLetto().getID_letto());
        return true;
        // logica di registrazione ricoveri con controllo sovrapposizione letti
        //True se il letto è libero
    }


    public List<Medico> elencoSostituzioni(AssenzaMedico assenza) {
        System.out.println("DB Query: Ricerca medici dello stesso reparto liberi durante l'assenza di: " + assenza.getMedico().getUsername());
        return new ArrayList<Medico>();
        // Riceve l'oggetto della malattia inserito e RESTITUISCE una lista reale
        // di Medici sostitutivi
    }

    @Override
    public void mostraInfo() {
        System.out.println("Amministratore: " + getUsername());
    }
}
