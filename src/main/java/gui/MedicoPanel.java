package gui;

import controller.Controller;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public class MedicoPanel {

    private JPanel mainPanel;
    private JButton btnAgendaGiornaliera;
    private JButton btnAgendaSettimanale;
    private JButton btnRegistraPrestazione;
    private JButton btnLogout;

    private Controller controller;
    private JFrame frame;            // La finestra corrente (Area Medica)
    private JFrame frameChiamante;   // La finestra di Login per consentire il ritorno al logout

    public MedicoPanel(Controller controller, JFrame frame, JFrame frameChiamante, String matricolaMedico) {
        this.controller = controller;
        this.frame = frame;
        this.frameChiamante = frameChiamante;

        // Gestione della visualizzazione dell'agenda giornaliera delle prestazioni
        btnAgendaGiornaliera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Istanziamo il nuovo pannello dell'agenda passando controller, il frame attuale e la matricola
                AgendaGiornalieraPanel agendaPanel = new AgendaGiornalieraPanel(controller, frame, matricolaMedico);

                // 2. Rendiamo visibile la JTable dell'agenda
                agendaPanel.getFrame().setVisible(true);

                // 3. Nascondiamo momentaneamente il menu principale del medico
                frame.setVisible(false);
            }
        });

        // Gestione della visualizzazione dell'agenda settimanale delle prestazioni
        btnAgendaSettimanale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Passiamo lo username del medico (che abbiamo verificato arrivi correttamente dal login)
                AgendaSettimanalePanel settimanalePanel = new AgendaSettimanalePanel(controller, frame, matricolaMedico);
                settimanalePanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Gestione della registrazione di una nuova prestazione medica sul paziente
        btnRegistraPrestazione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
                // acquisizione dati tramite finestre di dialogo grafiche modali
                String tipo = JOptionPane.showInputDialog(frame, "Inserisci il tipo di prestazione (es. Visita Cardiologica):");
                if (tipo == null || tipo.trim().isEmpty()) return;

                // Oggetti finti di supporto per simulare le associazioni del Model necessarie alla compilazione
                Reparto repartoFinto = new Reparto(1, "Cardiologia");
                Medico medicoCorrente = new Medico("rossi.mario", "pwd", "MAT001", repartoFinto);
                Paziente pazienteFinto = new Paziente("CF999X", "NomePaziente", "CognomePaziente");
                Letto lettoFinto = new Letto(5);
                Ricovero ricoveroAttivo = new Ricovero(1, pazienteFinto, lettoFinto, LocalDate.now(), LocalTime.now(), LocalDate.now().plusDays(2), LocalTime.now());

                // Impacchettamento dei dati grezzi all'interno dell'oggetto Entity corrispondente
                Prestazione nuovaPrestazione = new Prestazione(
                        tipo.trim(),
                        LocalTime.now(),
                        LocalTime.now().plusHours(1),
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
                        LocalDate.now(),
                        LocalTime.of(9, 0),
                        LocalTime.of(13, 0)
                );

                if (libero) {
                    JOptionPane.showMessageDialog(frame, "Il medico risulta libero ed è disponibile nella fascia indicata.",
                            "Stato Disponibilità", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Il medico risulta già occupato in altre prestazioni o assente.",
                            "Stato Disponibilità", JOptionPane.WARNING_MESSAGE);
                }
=======
                RegistraPrestazionePanel registraPanel = new RegistraPrestazionePanel(controller, frame, matricolaMedico);
                registraPanel.getFrame().setVisible(true);
                frame.setVisible(false);
>>>>>>> cf645cd16956947baae5210bd08c2cd2a50d227e
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        btnAgendaGiornaliera = new JButton();
        btnAgendaGiornaliera.setText("Agenda Giornaliera");
        mainPanel.add(btnAgendaGiornaliera, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnLogout = new JButton();
        btnLogout.setText("Logout");
        mainPanel.add(btnLogout, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDisponibilità = new JButton();
        btnDisponibilità.setText("Disponibilità");
        mainPanel.add(btnDisponibilità, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnRegistraPrestazione = new JButton();
        btnRegistraPrestazione.setText("Registra Prestazione");
        mainPanel.add(btnRegistraPrestazione, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnAgendaSettimanale = new JButton();
        btnAgendaSettimanale.setText("Agenda Settimanale");
        mainPanel.add(btnAgendaSettimanale, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}