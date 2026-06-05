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

    // Il costruttore riceve il controller e il riferimento alla finestra precedente per poter tornare indietro
    public PazientePanel(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        // Configurazione autonoma del JFrame associato a questa form
        frame = new JFrame("Gestione Anagrafica Paziente");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(frameChiamante); // Centra la finestra rispetto alla precedente

        // Azione del tasto Salva (Esegue l'UPSERT sul DB e torna indietro)
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

                Paziente paziente = new Paziente(cf, nome, cognome);
                boolean esito = controller.gestisciPazienti(paziente);

                if (esito) {
                    JOptionPane.showMessageDialog(frame, "Dati del paziente salvati con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);

                    // Chiude la finestra corrente e ripristina la schermata amministratore
                    frame.setVisible(false);
                    frameChiamante.setVisible(true);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Errore durante il salvataggio sul database.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Azione del tasto Annulla (Torna indietro senza salvare nulla)
        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frameChiamante.setVisible(true);
                frame.dispose(); // Distrugge la finestra per liberare memoria RAM
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }
}