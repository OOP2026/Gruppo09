package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TestModel {

	public static void main(String[] args) {

		// Creazione utenti
		Admin admin = new Admin("admin1", "pass123");
		Medico medico = new Medico("mario.rossi", "pass456", "MAT001");

		// Test login
		admin.login("admin1", "pass123");
		medico.login("mario.rossi", "pass456");

		// Creazione struttura ospedaliera
		Reparto reparto = new Reparto(1, "Cardiologia");
		Stanza stanza = new Stanza(101, 3);
		Letto letto = new Letto(1);

		// Collegamento struttura
		stanza.letti.add(letto);
		reparto.stanze.add(stanza);

		// Mostra info struttura
		reparto.mostraInfo();
		stanza.mostraInfo();
		letto.mostraInfo();

		// Creazione paziente
		Paziente paziente = new Paziente("RSSMRA80A01F839X", "Mario", "Bianchi");
		paziente.mostraInfo();

		// Creazione ricovero
		Ricovero ricovero = new Ricovero(
				1,
				paziente, letto,
				LocalDate.of(2024, 1, 10), LocalTime.of(9, 0),
				LocalDate.of(2024, 1, 15), LocalTime.of(12, 0)
		);
		ricovero.mostraInfo();
		ricovero.checkSovrapposizione();
		ricovero.inCorso();

		// Creazione turno
		Turno turno = new Turno(
				1,
				LocalTime.of(8, 0), LocalTime.of(14, 0),
				"Lunedi", medico
		);
		turno.mostraInfo();
		turno.copreFascia(LocalTime.of(10, 0));

		// Creazione prestazione
		Prestazione prestazione = new Prestazione(
				"Visita",
				LocalTime.of(10, 0), LocalTime.of(11, 0),
				medico, ricovero
		);
		prestazione.mostraInfo();
		prestazione.modificaEsito("Paziente in buone condizioni");

		// Agenda medico
		medico.agendaGiornaliera();
		medico.agendaSettimanale();

		// Assenza medico
		AssenzaMedico assenza = new AssenzaMedico(
				medico,
				LocalDate.of(2024, 1, 12),
				LocalDate.of(2024, 1, 14)
		);
		assenza.mostraInfo();
		assenza.getTurniScoperti();

		// Funzioni admin
		admin.gestisciPazienti();
		admin.gestisciRicoveri();
		admin.elencoSostituzioni();

		// Ricerca letti liberi
		reparto.cercaLettiLiberi();
	}
}