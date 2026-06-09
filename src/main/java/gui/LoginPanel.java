package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pannello di login del sistema ospedaliero.
 * Raccoglie le credenziali dell'utente e smista verso la dashboard
 * corretta in base al ruolo restituito dal Controller.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class LoginPanel {

    private JPanel mainPanel;
    private JTextField txtUsernameTextField;
    private JPasswordField txtPasswordPasswordField;
    private JButton btnloginButton;

    private Controller controller;
    private JFrame frame;

    /**
     * Costruisce il pannello di login e registra il listener sul pulsante di accesso.
     *
     * @param controller coordinatore centrale del sistema
     * @param frame      finestra principale dell'applicazione
     */
    public LoginPanel(Controller controller, JFrame frame) {
        this.controller = controller;
        this.frame = frame;

        btnloginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsernameTextField.getText().trim();
                String password = new String(txtPasswordPasswordField.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Inserisci username e password!");
                    return;
                }

                // Verifica le credenziali e ottiene il ruolo dal controller
                String ruolo = controller.login(username, password);

                if (ruolo.equals("admin")) {
                    JOptionPane.showMessageDialog(frame, "Benvenuto Admin: " + username);

                    JFrame adminFrame = new JFrame("Area Amministratore");
                    AdminPanel adminPanel = new AdminPanel(controller, adminFrame, frame);
                    adminPanel.getMainPanel().setPreferredSize(new Dimension(600, 450));
                    adminFrame.setContentPane(adminPanel.getMainPanel());
                    adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    adminFrame.pack();
                    adminFrame.setLocationRelativeTo(frame);
                    adminFrame.setVisible(true);
                    frame.setVisible(false);

                } else if (ruolo.equals("medico")) {
                    JOptionPane.showMessageDialog(frame, "Benvenuto Medico: " + username);

                    JFrame medicoFrame = new JFrame("Area Medica");
                    MedicoPanel medicoPanel = new MedicoPanel(controller, medicoFrame, frame);
                    medicoPanel.getMainPanel().setPreferredSize(new Dimension(600, 450));
                    medicoFrame.setContentPane(medicoPanel.getMainPanel());
                    medicoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    medicoFrame.pack();
                    medicoFrame.setLocationRelativeTo(frame);
                    medicoFrame.setVisible(true);
                    frame.setVisible(false);

                } else {
                    JOptionPane.showMessageDialog(frame, "Username o password errati!",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * @return pannello radice del login
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}