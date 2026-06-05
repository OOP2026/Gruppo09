package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< HEAD
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

=======
>>>>>>> cf645cd16956947baae5210bd08c2cd2a50d227e

public class AdminPanel {

    private JPanel mainPanel;
    private JButton btnGestisciPazienti;
    private JButton btnGestisciRicoveri;
    private JButton btnElencoSostituzioni;
    private JButton btnLogout;

    private JTextField txtCodiceFiscale;
    private JTextField txtNome;
    private JTextField txtCognome;

    private Controller controller;
    private JFrame frame;            // Finestra corrente dell'area amministratore
    private JFrame frameChiamante;   // Finestra di login precedente per consentire il ritorno al logout

    // Costruttore principale per l'inizializzazione dei componenti e dei listener
    public AdminPanel(Controller controller, JFrame frame, JFrame frameChiamante) {
        this.controller = controller;
        this.frame = frame;
        this.frameChiamante = frameChiamante;

        // Apertura della schermata per la gestione dell'anagrafica pazienti
        btnGestisciPazienti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PazientePanel pazientePanel = new PazientePanel(controller, frame);
                pazientePanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Apertura della schermata per la registrazione e controllo dei ricoveri
        btnGestisciRicoveri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
                // Esempio di interazione simulata: in futuro questi dati arriveranno da una form dedicata (es. NuovoRicoveroPanel)
                // Al momento creiamo oggetti finti coerenti con i vincoli del Model per testare il flusso
                Paziente pazienteTest = new Paziente("CF12345", "TestNome", "TestCognome");
                Letto lettoTest = new Letto(10);

                Ricovero nuovoRicovero = new Ricovero(
                        101,
                        pazienteTest,
                        lettoTest,
                        LocalDate.now(),
                        LocalTime.now(),
                        LocalDate.now().plusDays(5),
                        LocalTime.of(12, 0)
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
=======
                RicoveroPanel ricoveroPanel = new RicoveroPanel(controller, frame);
                ricoveroPanel.getFrame().setVisible(true);
                frame.setVisible(false);
>>>>>>> cf645cd16956947baae5210bd08c2cd2a50d227e
            }
        });

        // Apertura della schermata per la ricerca dei medici sostituti disponibili
        btnElencoSostituzioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
                // Configurazione di un'istanza di assenza per simulare la richiesta di sostituzione
                Reparto repartoTest = new Reparto(1, "Cardiologia");
                Medico medicoAssente = new Medico("rossi.mario", "pwd", "MAT001", repartoTest);
                AssenzaMedico praticaAssenza = new AssenzaMedico(medicoAssente, LocalDate.now(), LocalDate.now().plusDays(3));

                // Il Controller interroga il Model e restituisce una lista reale (List<Medico>)
                List<Medico> mediciDisponibili = controller.elencoSostituzioni(praticaAssenza);

                JOptionPane.showMessageDialog(frame, "Ricerca sostituzioni completata.\nMedici liberi trovati nel reparto: " + mediciDisponibili.size(),
                        "Esito Ricerca", JOptionPane.INFORMATION_MESSAGE);
                // La lista ottenuta in futuro verrà passata al modulo JTable per popolare l'elenco a schermo
=======
                SostituzioniPanel sostituzioniPanel = new SostituzioniPanel(controller, frame);
                sostituzioniPanel.getFrame().setVisible(true);
                frame.setVisible(false);
>>>>>>> cf645cd16956947baae5210bd08c2cd2a50d227e
            }
        });

        // Gestione del logout con riattivazione del frame di login e distruzione del frame corrente
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.logout();
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

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
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        btnLogout = new JButton();
        btnLogout.setText("Logout");
        mainPanel.add(btnLogout, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnElencoSostituzioni = new JButton();
        btnElencoSostituzioni.setText("Elenco Sostituzioni");
        mainPanel.add(btnElencoSostituzioni, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnGestisciRicoveri = new JButton();
        btnGestisciRicoveri.setText("Gestisci Ricoveri");
        mainPanel.add(btnGestisciRicoveri, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnGestisciPazienti = new JButton();
        btnGestisciPazienti.setText("Gestisci Pazienti");
        mainPanel.add(btnGestisciPazienti, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}