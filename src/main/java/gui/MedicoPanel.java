package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MedicoPanel {

    private JPanel mainPanel;
    private JButton btnAgendaGiornaliera;
    private JButton btnAgendaSettimanale;
    private JButton btnRegistraPrestazione;
    private JButton btnLogout;

    private Controller controller;
    private JFrame frame;            // Finestra corrente dell'area medica
    private JFrame frameChiamante;   // Finestra di login precedente

    // Il costruttore accetta solo 3 parametri, rispettando l'assenza di dati tra finestre
    public MedicoPanel(Controller controller, JFrame frame, JFrame frameChiamante) {
        this.controller = controller;
        this.frame = frame;
        this.frameChiamante = frameChiamante;

        // Apertura della schermata agenda giornaliera
        btnAgendaGiornaliera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgendaGiornalieraPanel agendaPanel = new AgendaGiornalieraPanel(controller, frame);
                agendaPanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Apertura della schermata agenda settimanale
        btnAgendaSettimanale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgendaSettimanalePanel settimanalePanel = new AgendaSettimanalePanel(controller, frame);
                settimanalePanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Apertura della schermata di registrazione prestazioni
        btnRegistraPrestazione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistraPrestazionePanel registraPanel = new RegistraPrestazionePanel(controller, frame);
                registraPanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Gestione del logout con ripristino del login e distruzione della sessione
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.logout();
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose(); // Dealloca la finestra dalla memoria RAM
            }
        });
    }

    // Fornisce il pannello radice richiesto dal gestore delle finestre
    public JPanel getMainPanel() {
        return mainPanel;
    }
}