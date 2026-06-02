package gui;

import controller.Controller;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Sistema Ospedaliero");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        Controller controller = new Controller();

        frame.setContentPane(new LoginPanel(controller).getMainPanel());

        frame.setVisible(true);
    }
}