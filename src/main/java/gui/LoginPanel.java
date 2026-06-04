package gui;

import controller.Controller;
import javax.swing.*;
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

        // Ascoltatore reattivo associato al click del pulsante di login
        btnloginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Estrazione del testo inserito dall'utente nei campi grafici
                String username = txtUsernameTextField.getText().trim();
                String password = new String(txtPasswordPasswordField.getPassword()).trim();

                // Validazione locale dell'input per impedire l'invio di stringhe vuote
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Inserisci username e password!");
                    return;
                }

                // Inoltro delle credenziali al Controller (Principio della leggerezza della GUI)
                String ruolo = controller.login(username, password);

                // Smistamento dei flussi di navigazione in base alla risposta del Controller
                if (ruolo.equals("admin")) {
                    JOptionPane.showMessageDialog(frame, "Benvenuto Admin: " + username);

                    // Creazione della cornice indipendente per l'area amministratore
                    JFrame adminFrame = new JFrame("Area Amministratore");
                    AdminPanel adminPanel = new AdminPanel(controller, adminFrame, frame);

                    // Impostazione della dimensione preferita di base per il pannello dell'amministratore
                    adminPanel.getMainPanel().setPreferredSize(new java.awt.Dimension(600, 450));

                    // Configurazione del frame e passaggio del controllo visivo
                    adminFrame.setContentPane(adminPanel.getMainPanel());
                    adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    adminFrame.pack();
                    adminFrame.setLocationRelativeTo(frame);
                    adminFrame.setVisible(true);
                    frame.setVisible(false);

                } else if (ruolo.equals("medico")) {
                    JOptionPane.showMessageDialog(frame, "Benvenuto Medico: " + username);

                    // Creazione della cornice indipendente per l'area medica
                    JFrame medicoFrame = new JFrame("Area Medica");
                    MedicoPanel medicoPanel = new MedicoPanel(controller, medicoFrame, frame);

                    // Impostazione della dimensione preferita di base per il pannello del medico
                    medicoPanel.getMainPanel().setPreferredSize(new java.awt.Dimension(600, 450));

                    // Configurazione del frame e passaggio del controllo visivo
                    medicoFrame.setContentPane(medicoPanel.getMainPanel());
                    medicoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    medicoFrame.pack();
                    medicoFrame.setLocationRelativeTo(frame);
                    medicoFrame.setVisible(true);
                    frame.setVisible(false);

                } else {
                    // Feedback visivo di errore bloccante
                    JOptionPane.showMessageDialog(frame, "Username o password errati!",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Restituisce il pannello grafico principale per poterlo inserire all'interno della finestra (JFrame)
    public JPanel getMainPanel() {
        return mainPanel;
    }
}