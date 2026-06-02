package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;   //
import java.awt.event.ActionListener; //

public class AdminPanel {

    private JPanel mainPanel;
    private JButton btnGestisciPazienti;
    private JButton btnGestisciRicoveri;
    private JButton btnElencoSostituzioni;
    private JButton btnLogout;
    private Controller controller;

    public AdminPanel(Controller controller) {
        this.controller = controller;


        btnGestisciPazienti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminPanel.this.controller.gestisciPazienti();
                JOptionPane.showMessageDialog(null, "Gestione pazienti avviata!");
            }
        });


        btnGestisciRicoveri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminPanel.this.controller.gestisciRicoveri();
                JOptionPane.showMessageDialog(null, "Gestione ricoveri avviata!");
            }
        });

        btnElencoSostituzioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminPanel.this.controller.elencoSostituzioni();
                JOptionPane.showMessageDialog(null, "Ricerca sostituzioni avviata!");
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminPanel.this.controller.logout();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
                frame.setContentPane(new LoginPanel(AdminPanel.this.controller).getMainPanel());
                frame.revalidate();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}