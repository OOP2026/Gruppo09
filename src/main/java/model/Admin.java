package model;

import dao.PazienteDAO;
import dao.RicoveroDAO;
import java.util.List;

public class Admin extends Utente {

    public Admin(String username, String password) {
        super(username, password);
    }

    // Riceve il DAO come interfaccia astratta per non accoppiare la classe a PostgreSQL
    public boolean gestisciPazienti(Paziente paziente, PazienteDAO pazienteDao) {
        return pazienteDao.inserisciPaziente(paziente);
    }

    // Riceve il DAO come interfaccia astratta: esegue il controllo e poi inserisce
    public boolean gestisciRicoveri(Ricovero ricovero, RicoveroDAO ricoveroDao) {
        if (ricoveroDao.checkSovrapposizione(ricovero)) {
            return false;
        }
        return ricoveroDao.inserisciRicovero(ricovero);
    }

    // Il pannello Sostituzioni userà direttamente il metodo del MedicoDAO tramite il controller,
    // mantenendo questo metodo pulito
    public List<Medico> elencoSostituzioni(String matricolaAssente, java.time.LocalDate inizio, java.time.LocalDate fine, dao.MedicoDAO medicoDao) {
        return medicoDao.getSostitutiIdonei(matricolaAssente, inizio, fine);
    }

    @Override
    public void mostraInfo() {
        System.out.println("Amministratore: " + getUsername());
    }
}