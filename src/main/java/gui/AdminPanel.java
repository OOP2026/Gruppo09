package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                RicoveroPanel ricoveroPanel = new RicoveroPanel(controller, frame);
                ricoveroPanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Apertura della schermata per la ricerca dei medici sostituti disponibili
        btnElencoSostituzioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SostituzioniPanel sostituzioniPanel = new SostituzioniPanel(controller, frame);
                sostituzioniPanel.getFrame().setVisible(true);
                frame.setVisible(false);
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
}