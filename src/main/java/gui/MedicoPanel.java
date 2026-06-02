package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;   //
import java.awt.event.ActionListener; //

public class MedicoPanel {

    private JPanel mainPanel;
    private JButton btnAgendaGiornaliera;
    private JButton btnAgendaSettimanale;
    private JButton btnRegistraPrestazione;
    private JButton btnDisponibilità;
    private JButton btnLogout;
    private Controller controller;

    public MedicoPanel(Controller controller) {
        this.controller = controller;

        btnAgendaGiornaliera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MedicoPanel.this.controller.agendaGiornaliera();
                JOptionPane.showMessageDialog(null, "Agenda giornaliera avviata!");
            }
        });


        btnAgendaSettimanale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MedicoPanel.this.controller.agendaSettimanale();
                JOptionPane.showMessageDialog(null, "Agenda settimanale avviata!");
            }
        });


        btnRegistraPrestazione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MedicoPanel.this.controller.registraPrestazione();
                JOptionPane.showMessageDialog(null, "Registrazione prestazione avviata!");
            }
        });


        btnDisponibilità.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MedicoPanel.this.controller.disponibilita();
                JOptionPane.showMessageDialog(null, "Disponibilità avviata!");
            }
        });


        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MedicoPanel.this.controller.logout();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
                frame.setContentPane(new LoginPanel(MedicoPanel.this.controller).getMainPanel());
                frame.revalidate();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}