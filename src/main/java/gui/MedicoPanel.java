package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MedicoPanel extends JPanel {

    public MedicoPanel(Controller controller) {
        setLayout(new GridLayout(5, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnAgendaGiornaliera = new JButton("Agenda Giornaliera");
        JButton btnAgendaSettimanale = new JButton("Agenda Settimanale");
        JButton btnRegistraPrestazione = new JButton("Registra Prestazione");
        JButton btnDisponibilita = new JButton("Disponibilità");
        JButton btnLogout = new JButton("Logout");

        add(btnAgendaGiornaliera);
        add(btnAgendaSettimanale);
        add(btnRegistraPrestazione);
        add(btnDisponibilita);
        add(btnLogout);

        btnAgendaGiornaliera.addActionListener(e -> {
            controller.agendaGiornaliera();
            JOptionPane.showMessageDialog(null, "Agenda giornaliera avviata!");
        });

        btnAgendaSettimanale.addActionListener(e -> {
            controller.agendaSettimanale();
            JOptionPane.showMessageDialog(null, "Agenda settimanale avviata!");
        });

        btnRegistraPrestazione.addActionListener(e -> {
            controller.registraPrestazione();
            JOptionPane.showMessageDialog(null, "Registrazione prestazione avviata!");
        });

        btnDisponibilita.addActionListener(e -> {
            controller.disponibilita();
            JOptionPane.showMessageDialog(null, "Disponibilità avviata!");
        });

        btnLogout.addActionListener(e -> {
            controller.logout();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new LoginPanel(controller));
            frame.revalidate();
        });
    }
}