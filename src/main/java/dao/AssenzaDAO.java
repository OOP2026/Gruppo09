package dao;

import java.time.LocalDate;

public interface AssenzaDAO {
    // Registra un periodo di assenza per malattia di un medico
    boolean inserisciAssenza(String codMedico, LocalDate dataInizio, LocalDate dataFine);

    // Conta i ricoveri attivi in un reparto tramite procedura con parametro OUT
    int contaRicoveriAttivi(int idReparto);
}