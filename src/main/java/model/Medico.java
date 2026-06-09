package model;

/**
 * Rappresenta un medico del sistema ospedaliero.
 * Estende Utente e aggiunge matricola e reparto di afferenza.
 * Ha accesso alle funzionalità di agenda e registrazione prestazioni.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class Medico extends Utente {
    private String matricola;
    private Reparto reparto;

    /**
     * Costruisce un medico con le informazioni fornite.
     *
     * @param username  nome utente univoco per l'accesso al sistema
     * @param password  password associata all'account
     * @param matricola codice identificativo univoco del medico
     * @param reparto   reparto di afferenza del medico
     */
    public Medico(String username, String password, String matricola, Reparto reparto) {
        super(username, password);
        this.matricola = matricola;
        this.reparto = reparto;
    }

    /**
     * Controlla in memoria se il medico appartiene al reparto indicato.
     *
     * @param idReparto identificativo del reparto da verificare
     * @return true se il medico appartiene a quel reparto, false altrimenti
     */
    public boolean appartieneAReparto(int idReparto) {
        return reparto != null && reparto.getIdReparto() == idReparto;
    }

    /**
     * Stampa le informazioni del medico incluse matricola e reparto.
     */
    @Override
    public void mostraInfo() {
        String nomeReparto = (reparto != null) ? reparto.getNome() : "Nessun reparto";
        System.out.println("Medico: " + getUsername() +
                " | Matricola: " + matricola +
                " | Reparto: " + nomeReparto);
    }

    /**
     * @return matricola del medico
     */
    public String getMatricola() { return matricola; }

    /**
     * @param matricola nuova matricola da impostare
     */
    public void setMatricola(String matricola) { this.matricola = matricola; }

    /**
     * @return reparto di afferenza del medico
     */
    public Reparto getReparto() { return reparto; }

    /**
     * @param reparto nuovo reparto da impostare
     */
    public void setReparto(Reparto reparto) { this.reparto = reparto; }
}