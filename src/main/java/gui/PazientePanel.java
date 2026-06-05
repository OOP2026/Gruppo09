package gui;

import controller.Controller;
import model.Paziente;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public PazientePanel(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        // Configurazione della finestra per la gestione del paziente
        frame = new JFrame("Gestione Anagrafica Paziente");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(frameChiamante);

        // Gestione del click sul pulsante Salva
        btnSalva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Recupero dei testi inseriti dall'utente
                String cf = txtCodiceFiscale.getText().trim();
                String nome = txtNome.getText().trim();
                String cognome = txtCognome.getText().trim();

                // Validazione locale per verificare che nessun campo sia vuoto
                if (cf.isEmpty() || nome.isEmpty() || cognome.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Compila tutti i campi prima di salvare!", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Creazione del modello ed invio dei dati al controller
                Paziente paziente = new Paziente(cf, nome, cognome);
                boolean esito = controller.gestisciPazienti(paziente);

                if (esito) {
                    JOptionPane.showMessageDialog(frame, "Dati del paziente salvati con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);

                    // Ritorno alla dashboard amministratore e chiusura della finestra attuale
                    frame.setVisible(false);
                    frameChiamante.setVisible(true);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Errore durante il salvataggio sul database.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Gestione del click sul pulsante Annulla
        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ritorno alla schermata precedente senza salvare modifiche
                frame.setVisible(false);
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }
}