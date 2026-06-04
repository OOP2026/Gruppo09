package gui;

import controller.Controller;
import model.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class AdminPanel {

    private JPanel mainPanel;
    private JButton btnGestisciPazienti;
    private JButton btnGestisciRicoveri;
    private JButton btnElencoSostituzioni;
    private JButton btnLogout;

    private Controller controller;
    private JFrame frame;            // La finestra corrente (Area Amministratore)
    private JFrame frameChiamante;   // La finestra di Login per consentire il ritorno al logout


    public AdminPanel(Controller controller, JFrame frame, JFrame frameChiamante) {
        this.controller = controller;
        this.frame = frame;
        this.frameChiamante = frameChiamante;

        // Gestione dell'azione di inserimento/modifica di un paziente
        btnGestisciPazienti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // acquisizione dati tramite finestre di dialogo grafiche modali
                String nome = JOptionPane.showInputDialog(frame, "Inserisci il nome del paziente:");
                if (nome == null || nome.trim().isEmpty()) return;

                String cognome = JOptionPane.showInputDialog(frame, "Inserisci il cognome del paziente:");
                if (cognome == null || cognome.trim().isEmpty()) return;

                String cf = JOptionPane.showInputDialog(frame, "Inserisci il Codice Fiscale del paziente:");
                if (cf == null || cf.trim().isEmpty()) return;

                // Impacchettamento dei dati grezzi all'interno dell'oggetto Entity corrispondente
                Paziente nuovoPaziente = new Paziente(cf.trim(), nome.trim(), cognome.trim());

                // Inoltro della richiesta al Controller e raccolta del verdetto boolean
                boolean esito = controller.gestisciPazienti(nuovoPaziente);

                if (esito) {
                    JOptionPane.showMessageDialog(frame, "Paziente registrato con successo nel sistema!",
                            "Operazione Completata", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Errore durante la registrazione del paziente.",
                            "Errore di Validazione", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Gestione della registrazione di un nuovo ricovero ospedaliero
        btnGestisciRicoveri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Esempio di interazione simulata: in futuro questi dati arriveranno da una form dedicata (es. NuovoRicoveroPanel)
                // Al momento creiamo oggetti finti coerenti con i vincoli del Model per testare il flusso
                Paziente pazienteTest = new Paziente("CF12345", "TestNome", "TestCognome");
                Letto lettoTest = new Letto(10);

                Ricovero nuovoRicovero = new Ricovero(
                        101,
                        pazienteTest,
                        lettoTest,
                        java.time.LocalDate.now(),
                        java.time.LocalTime.now(),
                        java.time.LocalDate.now().plusDays(5),
                        java.time.LocalTime.of(12, 0)
                );

                // Trasmissione dell'oggetto ricovero al Controller per la verifica delle sovrapposizioni
                boolean esito = controller.gestisciRicoveri(nuovoRicovero);

                if (esito) {
                    JOptionPane.showMessageDialog(frame, "Ricovero approvato. Posto letto prenotato con successo!",
                            "Operazione Completata", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Impossibile completare il ricovero: rilevata sovrapposizione temporale sul letto.",
                            "Incompatibilità Oraria", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Gestione della ricerca del personale medico sostitutivo
        btnElencoSostituzioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Configurazione di un'istanza di assenza per simulare la richiesta di sostituzione
                Reparto repartoTest = new Reparto(1, "Cardiologia");
                Medico medicoAssente = new Medico("rossi.mario", "pwd", "MAT001", repartoTest);
                AssenzaMedico praticaAssenza = new AssenzaMedico(medicoAssente, java.time.LocalDate.now(), java.time.LocalDate.now().plusDays(3));

                // Il Controller interroga il Model e restituisce una lista reale (List<Medico>)
                List<Medico> mediciDisponibili = controller.elencoSostituzioni(praticaAssenza);

                JOptionPane.showMessageDialog(frame, "Ricerca sostituzioni completata.\nMedici liberi trovati nel reparto: " + mediciDisponibili.size(),
                        "Esito Ricerca", JOptionPane.INFORMATION_MESSAGE);
                // La lista ottenuta in futuro verrà passata al modulo JTable per popolare l'elenco a schermo
            }
        });

        // Gestione della procedura di disconnessione (Logout)
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Resetta lo stato utente e cancella la sessione corrente all'interno del Controller
                controller.logout();

                // Ripristino coordinato della visibilità delle finestre
                frameChiamante.setVisible(true); // Rende nuovamente visibile la finestra di Login
                frame.setVisible(false);         // Nasconde l'area amministrativa corrente
                frame.dispose();                 // Distrugge la finestra corrente per liberare risorse di memoria RAM
            }
        });
    }


    // Restituisce il pannello grafico principale per poterlo inserire all'interno della finestra (JFrame)
    public JPanel getMainPanel() {
        return mainPanel;
    }
}