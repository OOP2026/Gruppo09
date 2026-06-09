package gui;

import controller.Controller;
import model.Paziente;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pannello per la gestione dell'anagrafica dei pazienti.
 * Permette di inserire un nuovo paziente o aggiornare i dati di uno esistente.
 * Accessibile solo agli amministratori.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class PazientePanel {
    private JPanel mainPanel;
    private JTextField txtCodiceFiscale;
    private JTextField txtNome;
    private JTextField txtCognome;
    private JButton btnSalva;
    private JButton btnAnnulla;

    private JFrame frame;
    private JFrame frameChiamante;
    private Controller controller;

    /**
     * Costruisce il pannello gestione paziente.
     *
     * @param controller     coordinatore centrale del sistema
     * @param frameChiamante finestra della dashboard admin da ripristinare alla chiusura
     */
    public PazientePanel(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        frame = new JFrame("Gestione Anagrafica Paziente");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(frameChiamante);

        btnSalva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cf = txtCodiceFiscale.getText().trim();
                String nome = txtNome.getText().trim();
                String cognome = txtCognome.getText().trim();

                if (cf.isEmpty() || nome.isEmpty() || cognome.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Compila tutti i campi prima di salvare!", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crea l'oggetto paziente e lo passa al controller per il salvataggio
                Paziente paziente = new Paziente(cf, nome, cognome);
                boolean esito = controller.gestisciPazienti(paziente);

                if (esito) {
                    JOptionPane.showMessageDialog(frame, "Dati del paziente salvati con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    frame.setVisible(false);
                    frameChiamante.setVisible(true);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Errore durante il salvataggio sul database.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }

    /**
     * @return finestra del pannello gestione paziente
     */
    public JFrame getFrame() {
        return frame;
    }
}