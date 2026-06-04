package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        // Inizializzazione della cornice reale della finestra principale dell'applicazione
        JFrame frame = new JFrame("Sistema Ospedaliero");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Istanziazione dell'oggetto unico di controllo globale
        Controller controller = new Controller();

        // Collegamento del pannello di Login passando il controller e la finestra corrente
        LoginPanel loginPanel = new LoginPanel(controller, frame);
        loginPanel.getMainPanel().setPreferredSize(new Dimension(450, 300));
        frame.setContentPane(loginPanel.getMainPanel());

        // Calcolo automatico delle dimensioni ottimali in base ai widget inseriti nella Form
        frame.pack();

        // Posizionamento centrato sul monitor dello schermo dell'utente
        frame.setLocationRelativeTo(null);

        // Attivazione e visualizzazione dell'interfaccia grafica a schermo
        frame.setVisible(true);
    }
}