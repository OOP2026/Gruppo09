package gui;

import controller.Controller;
import model.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class MedicoPanel {

    private JPanel mainPanel;
    private JButton btnAgendaGiornaliera;
    private JButton btnAgendaSettimanale;
    private JButton btnRegistraPrestazione;
    private JButton btnDisponibilità;
    private JButton btnLogout;

    private Controller controller;
    private JFrame frame;            // La finestra corrente (Area Medica)
    private JFrame frameChiamante;   // La finestra di Login per consentire il ritorno al logout

    public MedicoPanel(Controller controller, JFrame frame, JFrame frameChiamante) {
        this.controller = controller;
        this.frame = frame;
        this.frameChiamante = frameChiamante;

        // Gestione della visualizzazione dell'agenda giornaliera delle prestazioni
        btnAgendaGiornaliera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Il Controller interroga l'agenda del medico loggato e restituisce una lista reale (List<Prestazione>)
                List<Prestazione> prestazioniOggi = controller.agendaGiornaliera();

                JOptionPane.showMessageDialog(frame, "Agenda giornaliera caricata.\nPrestazioni programmate per oggi: " + prestazioniOggi.size(),
                        "Pianificazione Odierna", JOptionPane.INFORMATION_MESSAGE);
                // La lista ottenuta in futuro verrà passata al modulo JTable per popolare l'elenco a schermo
            }
        });

        // Gestione della visualizzazione dell'agenda settimanale delle prestazioni
        btnAgendaSettimanale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Il Controller interroga l'agenda settimanale e restituisce l'elenco delle prestazioni
                List<Prestazione> prestazioniSettimana = controller.agendaSettimanale();

                JOptionPane.showMessageDialog(frame, "Agenda settimanale caricata.\nPrestazioni totali programmate: " + prestazioniSettimana.size(),
                        "Pianificazione Settimanale", JOptionPane.INFORMATION_MESSAGE);
                // La lista ottenuta verrà usata per aggiornare la tabella grafica dei turni settimanali
            }
        });

        // Gestione della registrazione di una nuova prestazione medica sul paziente
        btnRegistraPrestazione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // acquisizione dati tramite finestre di dialogo grafiche modali
                String tipo = JOptionPane.showInputDialog(frame, "Inserisci il tipo di prestazione (es. Visita Cardiologica):");
                if (tipo == null || tipo.trim().isEmpty()) return;

                // Oggetti finti di supporto per simulare le associazioni del Model necessarie alla compilazione
                Reparto repartoFinto = new Reparto(1, "Cardiologia");
                Medico medicoCorrente = new Medico("rossi.mario", "pwd", "MAT001", repartoFinto);
                Paziente pazienteFinto = new Paziente("CF999X", "NomePaziente", "CognomePaziente");
                Letto lettoFinto = new Letto(5);
                Ricovero ricoveroAttivo = new Ricovero(1, pazienteFinto, lettoFinto, java.time.LocalDate.now(), java.time.LocalTime.now(), java.time.LocalDate.now().plusDays(2), java.time.LocalTime.now());

                // Impacchettamento dei dati grezzi all'interno dell'oggetto Entity corrispondente
                Prestazione nuovaPrestazione = new Prestazione(
                        tipo.trim(),
                        java.time.LocalTime.now(),
                        java.time.LocalTime.now().plusHours(1),
                        medicoCorrente,
                        ricoveroAttivo
                );

                // Inoltro della richiesta al Controller e raccolta del verdetto boolean
                boolean esito = controller.registraPrestazione(nuovaPrestazione);

                if (esito) {
                    JOptionPane.showMessageDialog(frame, "Prestazione medica registrata con successo!",
                            "Operazione Completata", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Errore nella registrazione della prestazione: vincoli orari violati.",
                            "Errore di Validazione", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Gestione della verifica di disponibilità oraria del medico corrente
        btnDisponibilità.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Configurazione di un medico di test per interrogarne gli impegni orari
                Reparto repartoFinto = new Reparto(1, "Cardiologia");
                Medico medicoDaVerificare = new Medico("rossi.mario", "pwd", "MAT001", repartoFinto);

                // Trasmissione dei parametri cronologici al Controller per controllare lo stato di reperibilità
                boolean libero = controller.disponibilita(
                        medicoDaVerificare,
                        java.time.LocalDate.now(),
                        java.time.LocalTime.of(9, 0),
                        java.time.LocalTime.of(13, 0)
                );

                if (libero) {
                    JOptionPane.showMessageDialog(frame, "Il medico risulta libero ed è disponibile nella fascia indicata.",
                            "Stato Disponibilità", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Il medico risulta già occupato in altre prestazioni o assente.",
                            "Stato Disponibilità", JOptionPane.WARNING_MESSAGE);
                }
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
                frame.setVisible(false);         // Nasconde l'area medica corrente
                frame.dispose();                 // Distrugge la finestra corrente per liberare risorse di memoria RAM
            }
        });
    }
    // Restituisce il pannello grafico principale per poterlo inserire all'interno della finestra (JFrame)
    public JPanel getMainPanel() {
        return mainPanel;
    }
}