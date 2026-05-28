package gui;

import controller.Controller;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sistema Ospedaliero");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLocationRelativeTo(null);

            Controller controller = new Controller();

            frame.setContentPane(new LoginPanel(controller));
            frame.setVisible(true);
        });
    }
}