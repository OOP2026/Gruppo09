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

        // Inizializzazione e configurazione del frame indipendente
        this.frame = new JFrame("Ricerca Medici Sostituti");
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configurazione delle colonne della JTable
        configuraTabella();

        // Caricamento dei medici all'interno della ComboBox
        popolaMedici();

        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);

        // Gestione del click sul pulsante Cerca
        btnCerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String medicoSelezionato = (String) cmbMediciAssenti.getSelectedItem();
                    String dataInizioRaw = txtDataInizio.getText().trim();
                    String dataFineRaw = txtDataFine.getText().trim();

                    // Controllo formale sui campi vuoti della form
                    if (medicoSelezionato == null || dataInizioRaw.isEmpty() || dataFineRaw.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Compila tutti i campi di ricerca!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Separazione della stringa per estrarre la sola matricola del medico assente
                    String matricola = medicoSelezionato.split(" - ")[0];
                    LocalDate inizio = LocalDate.parse(dataInizioRaw);
                    LocalDate fine = LocalDate.parse(dataFineRaw);

                    // Controllo di congruenza logica sulle date inserite
                    if (fine.isBefore(inizio)) {
                        JOptionPane.showMessageDialog(frame, "La data di fine non può essere precedente all'inizio!", "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Invocazione del metodo di business delegato al controller
                    List<Medico> sostituti = controller.calcolaSostituti(matricola, inizio, fine);

                    // Svuotamento preventivo e popolamento della tabella con i risultati
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

        // Gestione del click sul pulsante Annulla
        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ripristino visibilità della dashboard amministratore e deallocazione risorse
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

    private void configuraTabella() {
        // Definizione dello schema e dei titoli delle colonne della JTable
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Matricola Medico");
        tableModel.addColumn("Username / Nome");
        tableModel.addColumn("Reparto Afferenza");
        tblSostituti.setModel(tableModel);
    }

    private void popolaMedici() {
        // Recupero dell'elenco completo dei medici per valorizzare la tendina iniziale
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