package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.*;

/**
 * Entry point dell'applicazione ospedaliera.
 * Inizializza il Controller e avvia la schermata di login.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class Main {

    /**
     * Avvia l'applicazione inizializzando il Controller e mostrando il pannello di login.
     *
     * @param args argomenti da riga di comando (non utilizzati)
     */
    public static void main(String[] args) {

        // Inizializzazione del coordinatore unico del sistema
        Controller controller = new Controller();

        // Configurazione della finestra principale dell'applicazione
        JFrame frame = new JFrame("Sistema Ospedaliero");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Collegamento del pannello di login passando il controller e il frame
        LoginPanel loginPanel = new LoginPanel(controller, frame);
        loginPanel.getMainPanel().setPreferredSize(new Dimension(450, 300));
        frame.setContentPane(loginPanel.getMainPanel());

        // Adattamento automatico delle dimensioni in base ai componenti
        frame.pack();

        // Posizionamento centrato sullo schermo
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}