package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class RegistraPrestazionePanel {
    // Componenti legati al file .form
    private JPanel mainPanel;
    private JComboBox cmbRicovero;
    private JTextField txtIdRicovero;
    private JTextField txtTipo;
    private JTextField txtOraInizio;
    private JTextField txtOraFine;
    private JTextArea txtEsito;
    private JButton btnSalva;
    private JButton btnAnnulla;

    private JFrame frame;
    private JFrame frameChiamante;
    private Controller controller;
    private String usernameMedico;

    public RegistraPrestazionePanel(Controller controller, JFrame frameChiamante, String usernameMedico) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;
        this.usernameMedico = usernameMedico;

        this.frame = new JFrame("Registra Nuova Prestazione");
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        popolaMenuRicoveri();

        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);

        btnSalva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eseguiRegistrazione();
            }
        });

        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }

    private void eseguiRegistrazione() {
        try {
            String itemSelezionato = (String) cmbRicovero.getSelectedItem();
            if (itemSelezionato == null || itemSelezionato.startsWith("Nessun")) {
                JOptionPane.showMessageDialog(frame, "Seleziona un ricovero valido.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 1. Estraiamo l'ID prima del carattere "|"
            // Usiamo il limit a 2 per evitare problemi con spazi extra
            String[] parti = itemSelezionato.split("\\|");
            int idRicovero = Integer.parseInt(parti[0].trim());

            String tipo = txtTipo.getText().trim();
            String esito = txtEsito.getText().trim();

            if (tipo.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Il campo 'Tipo Visita' è obbligatorio.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Parsing degli orari
            LocalTime oraInizio = LocalTime.parse(txtOraInizio.getText().trim());
            LocalTime oraFine = LocalTime.parse(txtOraFine.getText().trim());

            if (!oraFine.isAfter(oraInizio)) {
                JOptionPane.showMessageDialog(frame, "L'ora di fine deve essere successiva all'ora di inizio.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Chiamata al controller
            String risultato = controller.registraNuovaPrestazione(usernameMedico, idRicovero, tipo, oraInizio, oraFine, esito);

            if (risultato.equals("OK")) {
                JOptionPane.showMessageDialog(frame, "Prestazione registrata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                frameChiamante.setVisible(true);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, risultato, "Vincolo Violato", JOptionPane.WARNING_MESSAGE);
            }

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(frame, "Formato orario non valido. Usa il formato HH:mm (es. 08:30).", "Errore Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // 👇 RETE DI SICUREZZA: Se c'è un errore qualsiasi (Null, conversioni, ecc.) esce questo pop-up!
            JOptionPane.showMessageDialog(frame, "Errore imprevisto durante il salvataggio:\n" + ex.toString(), "Errore di Sistema", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Stampa la traccia completa nella console Run
        }
    }

    private void popolaMenuRicoveri() {
        // Recuperiamo la lista di stringhe dal controller
        java.util.List<String> ricoveri = controller.recuperaRicoveriPerComboBox();

        // Stampiamo un debug in console per verificare se arrivano dati dal DB
        System.out.println("DEBUG: Ricoveri trovati nel DB: " + ricoveri.size());

        if (ricoveri.isEmpty()) {
            cmbRicovero.addItem("Nessun ricovero disponibile nel sistema");
            btnSalva.setEnabled(false); // Disabilita il tasto salva se non ci sono ricoveri
        } else {
            for (String r : ricoveri) {
                System.out.println("DEBUG: Aggiungo a ComboBox -> " + r);
                cmbRicovero.addItem(r); // Aggiunge l'elemento al menu a tendina
            }
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}