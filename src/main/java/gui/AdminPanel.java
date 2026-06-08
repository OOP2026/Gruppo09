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
    private JButton btnDimissioni;
    private JButton btnLettiReparto;
    private JButton btnAssenza;
    private JButton btnLogout;

    private Controller controller;
    private JFrame frame;
    private JFrame frameChiamante;

    public AdminPanel(Controller controller, JFrame frame, JFrame frameChiamante) {
        this.controller = controller;
        this.frame = frame;
        this.frameChiamante = frameChiamante;

        btnGestisciPazienti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PazientePanel pazientePanel = new PazientePanel(controller, frame);
                pazientePanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        btnGestisciRicoveri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RicoveroPanel ricoveroPanel = new RicoveroPanel(controller, frame);
                ricoveroPanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        btnElencoSostituzioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SostituzioniPanel sostituzioniPanel = new SostituzioniPanel(controller, frame);
                sostituzioniPanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Apre la schermata per visualizzare i pazienti in scadenza di dimissione
        btnDimissioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DimissioniPanel dimissioniPanel = new DimissioniPanel(controller, frame);
                dimissioniPanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Apre la schermata per la ricerca dei letti liberi in un reparto
        btnLettiReparto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LettiRepartoPanel lettiPanel = new LettiRepartoPanel(controller, frame);
                lettiPanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Apre la schermata per registrare un periodo di assenza di un medico
        btnAssenza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AssenzaPanel assenzaPanel = new AssenzaPanel(controller, frame);
                assenzaPanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

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