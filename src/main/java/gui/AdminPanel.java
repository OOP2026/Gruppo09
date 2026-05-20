package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {

    public AdminPanel(Controller controller) {
        setLayout(new GridLayout(4, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnGestisciPazienti = new JButton("Gestisci Pazienti");
        JButton btnGestisciRicoveri = new JButton("Gestisci Ricoveri");
        JButton btnElencoSostituzioni = new JButton("Elenco Sostituzioni");
        JButton btnLogout = new JButton("Logout");

        add(btnGestisciPazienti);
        add(btnGestisciRicoveri);
        add(btnElencoSostituzioni);
        add(btnLogout);

        btnGestisciPazienti.addActionListener(e -> {
            controller.gestisciPazienti();
            JOptionPane.showMessageDialog(null, "Gestione pazienti avviata!");
        });

        btnGestisciRicoveri.addActionListener(e -> {
            controller.gestisciRicoveri();
            JOptionPane.showMessageDialog(null, "Gestione ricoveri avviata!");
        });

        btnElencoSostituzioni.addActionListener(e -> {
            controller.elencoSostituzioni();
            JOptionPane.showMessageDialog(null, "Ricerca sostituzioni avviata!");
        });

        btnLogout.addActionListener(e -> {
            controller.logout();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new LoginPanel());
            frame.revalidate();
        });
    }
}