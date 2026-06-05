package model;

import java.util.List;

public class Medico extends Utente {
    private String matricola;
    private Reparto reparto;

    public Medico(String username, String password, String matricola, Reparto reparto) {
        super(username, password);
        this.matricola = matricola;
        this.reparto = reparto;
    }

    // Controlla in memoria se il medico appartiene al reparto passato come parametro
    public boolean appartieneAReparto(int idReparto) {
        return reparto != null && reparto.getIdReparto() == idReparto;
    }

    @Override
    public void mostraInfo() {
        String nomeReparto = (reparto != null) ? reparto.getNome() : "Nessun reparto";
        System.out.println("Medico: " + getUsername() +
                " | Matricola: " + matricola +
                " | Reparto: " + nomeReparto);
    }

    public String getMatricola() { return matricola; }
    public void setMatricola(String matricola) { this.matricola = matricola; }
    public Reparto getReparto() { return reparto; }
    public void setReparto(Reparto reparto) { this.reparto = reparto; }
}