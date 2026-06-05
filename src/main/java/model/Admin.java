package model;

import dao.PazienteDAO;
import dao.RicoveroDAO;
import implementazioneDao.PazientePostgresDAO;
import implementazioneDao.RicoveroPostgresDAO;

import java.util.ArrayList;
import java.util.List;

public class Admin extends Utente {

    public Admin(String username, String password) {
        super(username, password);
    }

    public boolean gestisciPazienti(Paziente paziente) {
        PazienteDAO pazienteDao = new PazientePostgresDAO();
        // Richiama l'operazione atomica del DAO (inserimento/modifica)
        return pazienteDao.inserisciPaziente(paziente);
    }

    public boolean gestisciRicoveri(Ricovero ricovero) {
        RicoveroDAO ricoveroDao = new RicoveroPostgresDAO();

        // 1. Chiede al database se ci sono sovrapposizioni temporali sul posto letto
        if (ricoveroDao.checkSovrapposizione(ricovero)) {
            return false; // Rifiuta la prenotazione
        }

        // 2. Se il posto è libero, procede con l'inserimento del record
        return ricoveroDao.inserisciRicovero(ricovero);
    }

    public List<Medico> elencoSostituzioni(AssenzaMedico assenza) {
        System.out.println("DB Query: Ricerca medici dello stesso reparto liberi durante l'assenza di: " + assenza.getMedico().getUsername());
        return new ArrayList<Medico>();
    }

    @Override
    public void mostraInfo() {
        System.out.println("Amministratore: " + getUsername());
    }
}