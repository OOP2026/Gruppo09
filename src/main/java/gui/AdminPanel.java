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

    private Controller controller;
    private JFrame frame;            // Finestra corrente della dashboard admin
    private JFrame frameChiamante;   // Finestra del login per consentire il ritorno

    public AdminPanel(Controller controller, JFrame frame, JFrame frameChiamante) {
        this.controller = controller;
        this.frame = frame;
        this.frameChiamante = frameChiamante;

        // Visualizzazione della schermata gestione pazienti
        btnGestisciPazienti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PazientePanel pazientePanel = new PazientePanel(controller, frame);
                pazientePanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Visualizzazione della schermata gestione ricoveri
        btnGestisciRicoveri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RicoveroPanel ricoveroPanel = new RicoveroPanel(controller, frame);
                ricoveroPanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Visualizzazione della schermata sostituzioni medici
        btnElencoSostituzioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SostituzioniPanel sostituzioniPanel = new SostituzioniPanel(controller, frame);
                sostituzioniPanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Gestione del logout: pulizia sessione, riapertura login e distruzione frame attuale
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.logout();
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose(); // Libera la memoria allocata per questa finestra
            }
        });
    }

    // Ritorna il pannello radice richiesto dal gestore delle finestre
    public JPanel getMainPanel() {
        return mainPanel;
    }
}