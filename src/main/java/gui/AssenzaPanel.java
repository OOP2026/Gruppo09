package gui;

import controller.Controller;
import model.Medico;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AssenzaPanel {
    private JPanel mainPanel;
    private JComboBox<String> cmbMedici;
    private JTextField txtDataInizio;
    private JTextField txtDataFine;
    private JButton btnSalva;
    private JButton btnAnnulla;

    private JFrame frame;
    private JFrame frameChiamante;
    private Controller controller;

    public AssenzaPanel(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        this.frame = new JFrame("Registra Assenza Medico");
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        popolaMedici();

        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);

        btnSalva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String medicoSelezionato = (String) cmbMedici.getSelectedItem();
                    String dataInizioRaw = txtDataInizio.getText().trim();
                    String dataFineRaw = txtDataFine.getText().trim();

                    if (medicoSelezionato == null || dataInizioRaw.isEmpty() || dataFineRaw.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Compila tutti i campi.", "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Estrae la matricola dal testo formattato della ComboBox
                    String matricola = medicoSelezionato.split(" - ")[0];
                    LocalDate dataInizio = LocalDate.parse(dataInizioRaw);
                    LocalDate dataFine = LocalDate.parse(dataFineRaw);

                    if (dataFine.isBefore(dataInizio)) {
                        JOptionPane.showMessageDialog(frame, "La data di fine non può essere prima della data di inizio.", "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    boolean esito = controller.inserisciAssenza(matricola, dataInizio, dataFine);

                    if (esito) {
                        JOptionPane.showMessageDialog(frame, "Assenza registrata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        frameChiamante.setVisible(true);
                        frame.setVisible(false);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Errore durante la registrazione.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Formato data non valido. Usa AAAA-MM-GG.", "Errore Formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

    private void popolaMedici() {
        // Recupera tutti i medici per la ComboBox
        List<Medico> medici = controller.recuperaTuttiMedici();
        if (medici != null) {
            for (Medico m : medici) {
                cmbMedici.addItem(m.getMatricola() + " - " + m.getUsername());
            }
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}