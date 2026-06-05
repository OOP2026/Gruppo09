package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel {

    private JPanel mainPanel;
    private JTextField txtUsernameTextField;
    private JPasswordField txtPasswordPasswordField;
    private JButton btnloginButton;

    private Controller controller;
    private JFrame frame;

    public LoginPanel(Controller controller, JFrame frame) {
        this.controller = controller;
        this.frame = frame;

        // Gestione dell'evento di login alla pressione del pulsante
        btnloginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Recupero e pulizia degli input inseriti dall'utente
                String username = txtUsernameTextField.getText().trim();
                String password = new String(txtPasswordPasswordField.getPassword()).trim();

                // Validazione formale dei campi obbligatori
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Inserisci username e password!");
                    return;
                }

                // Inoltro delle credenziali al controller per la verifica del ruolo
                String ruolo = controller.login(username, password);

                // Smistamento della navigazione in base al ruolo restituito
                if (ruolo.equals("admin")) {
                    JOptionPane.showMessageDialog(frame, "Benvenuto Admin: " + username);

                    // Inizializzazione della dashboard per l'amministratore
                    JFrame adminFrame = new JFrame("Area Amministratore");
                    AdminPanel adminPanel = new AdminPanel(controller, adminFrame, frame);

                    adminPanel.getMainPanel().setPreferredSize(new Dimension(600, 450));

                    // Configurazione della finestra e scambio di visibilità
                    adminFrame.setContentPane(adminPanel.getMainPanel());
                    adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    adminFrame.pack();
                    adminFrame.setLocationRelativeTo(frame);
                    adminFrame.setVisible(true);
                    frame.setVisible(false);

                } else if (ruolo.equals("medico")) {
                    JOptionPane.showMessageDialog(frame, "Benvenuto Medico: " + username);

                    // Inizializzazione della dashboard per il personale medico
                    JFrame medicoFrame = new JFrame("Area Medica");
                    MedicoPanel medicoPanel = new MedicoPanel(controller, medicoFrame, frame);

                    medicoPanel.getMainPanel().setPreferredSize(new Dimension(600, 450));

                    // Configurazione della finestra e scambio di visibilità
                    medicoFrame.setContentPane(medicoPanel.getMainPanel());
                    medicoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    medicoFrame.pack();
                    medicoFrame.setLocationRelativeTo(frame);
                    medicoFrame.setVisible(true);
                    frame.setVisible(false);

                } else {
                    // Notifica in caso di credenziali errate o non trovate
                    JOptionPane.showMessageDialog(frame, "Username o password errati!",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}