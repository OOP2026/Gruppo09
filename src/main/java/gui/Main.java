package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        // Inizializzazione del coordinatore unico del sistema
        Controller controller = new Controller();

        // Configurazione della finestra principale dell'applicazione
        JFrame frame = new JFrame("Sistema Ospedaliero");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Collegamento del pannello di Login iniziale passando il controller e il frame
        LoginPanel loginPanel = new LoginPanel(controller, frame);
        loginPanel.getMainPanel().setPreferredSize(new Dimension(450, 300));
        frame.setContentPane(loginPanel.getMainPanel());

        // Adattamento automatico delle dimensioni in base ai componenti
        frame.pack();

        // Posizionamento centrato dello schermo dell'utente
        frame.setLocationRelativeTo(null);

        // Visualizzazione della schermata di login a video
        frame.setVisible(true);
    }
}