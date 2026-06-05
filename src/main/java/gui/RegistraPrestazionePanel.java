package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class RegistraPrestazionePanel {
    private JPanel mainPanel;
    private JComboBox<String> cmbRicovero;
    private JTextField txtTipo;
    private JTextField txtOraInizio;
    private JTextField txtOraFine;
    private JTextArea txtEsito;
    private JButton btnSalva;
    private JButton btnAnnulla;

    private JFrame frame;
    private JFrame frameChiamante;
    private Controller controller;

    // Il costruttore accetta solo i riferimenti per il controllo visivo delle viste
    public RegistraPrestazionePanel(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        // Configurazione della finestra di registrazione
        this.frame = new JFrame("Registra Nuova Prestazione");
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Popolamento iniziale della tendina dei ricoveri attivi
        popolaMenuRicoveri();

        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);

        // Gestione del click sul pulsante Salva
        btnSalva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eseguiRegistrazione();
            }
        });

        // Gestione del click sul pulsante Annulla
        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.setVisible(false);
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

            // Isola l'ID numerico del ricovero tagliando la stringa della ComboBox
            String[] parti = itemSelezionato.split("\\|");
            int idRicovero = Integer.parseInt(parti[0].trim());

            String tipo = txtTipo.getText().trim();
            String esito = txtEsito.getText().trim();

            if (tipo.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Il campo 'Tipo Visita' è obbligatorio.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Conversione testuale delle fasce orarie inserite
            LocalTime oraInizio = LocalTime.parse(txtOraInizio.getText().trim());
            LocalTime oraFine = LocalTime.parse(txtOraFine.getText().trim());

            if (!oraFine.isAfter(oraInizio)) {
                JOptionPane.showMessageDialog(frame, "L'ora di fine deve essere successiva all'ora di inizio.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Delegazione dell'inserimento al controller senza passaggio di dati di sessione dalla GUI
            String risultato = controller.registraPrestazione(idRicovero, tipo, oraInizio, oraFine, esito);

            if (risultato.equals("OK")) {
                JOptionPane.showMessageDialog(frame, "Prestazione registrata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, risultato, "Vincolo Violato", JOptionPane.WARNING_MESSAGE);
            }

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(frame, "Formato orario non valido. Usa il formato HH:mm (es. 08:30).", "Errore Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore imprevisto durante il salvataggio:\n" + ex.getMessage(), "Errore di Sistema", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void popolaMenuRicoveri() {
        // Recupera i soli ricoveri attivi formattati in formato stringa
        List<String> ricoveri = controller.recuperaRicoveriPerComboBox();

        if (ricoveri.isEmpty()) {
            cmbRicovero.addItem("Nessun ricovero disponibile nel sistema");
            btnSalva.setEnabled(false);
        } else {
            for (String r : ricoveri) {
                cmbRicovero.addItem(r);
            }
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}