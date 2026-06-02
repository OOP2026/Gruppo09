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

    public LoginPanel(Controller controller) {
        this.controller = controller;


        btnloginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsernameTextField.getText().trim();
                String password = new String(txtPasswordPasswordField.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Inserisci username e password!");
                    return;
                }

                String ruolo = LoginPanel.this.controller.login(username, password);

                if (ruolo.equals("admin")) {
                    JOptionPane.showMessageDialog(null, "Benvenuto Admin: " + username);
                    LoginPanel.this.apriAdmin(LoginPanel.this.controller);

                } else if (ruolo.equals("medico")) {
                    JOptionPane.showMessageDialog(null, "Benvenuto Medico: " + username);
                    LoginPanel.this.apriMedico(LoginPanel.this.controller);

                } else {
                    JOptionPane.showMessageDialog(null, "Username o password errati!",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void apriAdmin(Controller controller) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        frame.setContentPane(new AdminPanel(controller).getMainPanel());
        frame.revalidate();
    }

    private void apriMedico(Controller controller) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        frame.setContentPane(new MedicoPanel(controller).getMainPanel());
        frame.revalidate();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}