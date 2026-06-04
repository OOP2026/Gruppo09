package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TestModel {

	public static void main(String[] args) {

		// Inizializzazione degli utenti e della struttura ospedaliera di test
		Admin admin = new Admin("admin1", "pass123");
		Reparto reparto = new Reparto(1, "Cardiologia");
		Medico medico = new Medico("mario.rossi", "pass456", "MAT001", reparto);

		// Esecuzione delle procedure di autenticazione per le diverse tipologie di utenza
		admin.login("admin1", "pass123");
		medico.login("mario.rossi", "pass456");

		// Configurazione della struttura fisica delle stanze e dei relativi posti letto
		Stanza stanza = new Stanza(101, 3);
		Letto letto = new Letto(1);

		// Associazione delle entità della struttura ospedaliera tramite l'uso dei rispettivi metodi getter
		stanza.getLetti().add(letto);
		reparto.getStanze().add(stanza);

		// Assegnazione del personale medico al reparto di afferenza con gestione della coerenza bidirezionale
		reparto.aggiungiMedico(medico);

		// Visualizzazione a console dei dati strutturali complessivi per attività di debug ordinaria
		reparto.mostraInfo();
		stanza.mostraInfo();
		letto.mostraInfo();

		// Registrazione e verifica dei dati anagrafici del paziente
		Paziente paziente = new Paziente("RSSMRA80A01F839X", "Mario", "Bianchi");
		paziente.mostraInfo();

		// Apertura di una nuova pratica di ricovero associata a un posto letto specifico
		Ricovero ricovero = new Ricovero(
				1,
				paziente, letto,
				LocalDate.of(2024, 1, 10), LocalTime.of(9, 0),
				LocalDate.of(2024, 1, 15), LocalTime.of(12, 0)
		);
		ricovero.mostraInfo();
		ricovero.checkSovrapposizione();
		ricovero.inCorso();

		// Definizione della pianificazione oraria del turno di guardia medica
		Turno turno = new Turno(
				1,
				LocalTime.of(8, 0), LocalTime.of(14, 0),
				"Lunedi", medico
		);
		turno.mostraInfo();
		turno.copreFascia(LocalTime.of(10, 0));

		// Registrazione di una prestazione medica vincolata al ricovero attivo del paziente
		Prestazione prestazione = new Prestazione(
				"Visita",
				LocalTime.of(10, 0), LocalTime.of(11, 0),
				medico, ricovero
		);
		prestazione.mostraInfo();
		prestazione.modificaEsito("Paziente in buone condizioni");

		// Interrogazione dei moduli di pianificazione delle agende del personale medico
		medico.agendaGiornaliera();
		medico.agendaSettimanale();

		// Inserimento del periodo di indisponibilità medica e contestuale individuazione dei turni scoperti
		AssenzaMedico assenza = new AssenzaMedico(
				medico,
				LocalDate.of(2024, 1, 12),
				LocalDate.of(2024, 1, 14)
		);
		assenza.mostraInfo();
		assenza.getTurniScoperti();

		// Esecuzione dei controlli di idoneità e delle operazioni amministrative di backend
		admin.gestisciPazienti(paziente);
		admin.gestisciRicoveri(ricovero);
		admin.elencoSostituzioni(assenza);

		// Verifica e report finale della disponibilità dei posti letto all'interno del reparto
		reparto.cercaLettiLiberi();
	}
}