package gui;

import controller.Controller;
import model.Medico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class SostituzioniPanel {
    private JPanel mainPanel;
    private JComboBox<String> cmbMediciAssenti;
    private JTextField txtDataInizio;
    private JTextField txtDataFine;
    private JButton btnCerca;
    private JTable tblSostituti;
    private JButton btnAnnulla;

    private JFrame frame;
    private JFrame frameChiamante;
    private Controller controller;
    private DefaultTableModel tableModel;

    public SostituzioniPanel(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        this.frame = new JFrame("Ricerca Medici Sostituti");
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configurazione iniziale della JTable della Form
        configuraTabella();

        // Popola la ComboBox con i medici censiti nel DB
        popolaMedici();

        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);

        // Azione del tasto Cerca Sostituti
        btnCerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String medicoSelezionato = (String) cmbMediciAssenti.getSelectedItem();
                    String dataInizioRaw = txtDataInizio.getText().trim();
                    String dataFineRaw = txtDataFine.getText().trim();

                    if (medicoSelezionato == null || dataInizioRaw.isEmpty() || dataFineRaw.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Compila tutti i campi di ricerca!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Estrae la matricola (assumendo che nella combo appaia come "MATRICOLA - USERNAME")
                    String matricola = medicoSelezionato.split(" - ")[0];
                    LocalDate inizio = LocalDate.parse(dataInizioRaw);
                    LocalDate fine = LocalDate.parse(dataFineRaw);

                    if (fine.isBefore(inizio)) {
                        JOptionPane.showMessageDialog(frame, "La data di fine non può essere precedente all'inizio!", "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Interroga il controller per trovare i sostituti idonei
                    List<Medico> sostituti = controller.calcolaSostituti(matricola, inizio, fine);

                    // Pulisce la tabella e carica i nuovi record trovati
                    tableModel.setRowCount(0);
                    if (sostituti.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Nessun medico disponibile trovato per questo periodo.", "Esito Ricerca", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        for (Medico m : sostituti) {
                            String nomeReparto = (m.getReparto() != null) ? m.getReparto().getNome() : "N/D";
                            tableModel.addRow(new Object[]{m.getMatricola(), m.getUsername(), nomeReparto});
                        }
                    }

                } catch (java.time.format.DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Usa il formato data corretto: AAAA-MM-GG", "Errore Formato", JOptionPane.ERROR_MESSAGE);
                }
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

    private void configuraTabella() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Matricola Medico");
        tableModel.addColumn("Username / Nome");
        tableModel.addColumn("Reparto Afferenza");
        tblSostituti.setModel(tableModel);
    }

    private void popolaMedici() {
        List<Medico> medici = controller.recuperaTuttiMedici();
        if (medici != null) {
            for (Medico m : medici) {
                cmbMediciAssenti.addItem(m.getMatricola() + " - " + m.getUsername());
            }
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}