package gui;

import controller.Controller;
import model.Medico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

/**
 * Pannello per la ricerca di medici sostitutivi durante un periodo di assenza.
 * I candidati vengono cercati tra i colleghi dello stesso reparto
 * che non hanno turni o prestazioni sovrapposte nel periodo indicato.
 * Accessibile solo agli amministratori.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
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

    /**
     * Costruisce il pannello sostituzioni e popola la ComboBox con i medici disponibili.
     *
     * @param controller     coordinatore centrale del sistema
     * @param frameChiamante finestra della dashboard admin da ripristinare alla chiusura
     */
    public SostituzioniPanel(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        this.frame = new JFrame("Ricerca Medici Sostituti");
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        configuraTabella();
        popolaMedici();

        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);

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

                    // Estrae la matricola dal testo formattato della ComboBox
                    String matricola = medicoSelezionato.split(" - ")[0];
                    LocalDate inizio = LocalDate.parse(dataInizioRaw);
                    LocalDate fine = LocalDate.parse(dataFineRaw);

                    if (fine.isBefore(inizio)) {
                        JOptionPane.showMessageDialog(frame, "La data di fine non può essere precedente all'inizio!", "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    List<Medico> sostituti = controller.calcolaSostituti(matricola, inizio, fine);

                    // Svuota la tabella e la ripopola con i risultati
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
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

    // Configura le colonne della tabella dei sostituti
    private void configuraTabella() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Matricola Medico");
        tableModel.addColumn("Username / Nome");
        tableModel.addColumn("Reparto Afferenza");
        tblSostituti.setModel(tableModel);
    }

    // Recupera tutti i medici dal controller e popola la ComboBox
    private void popolaMedici() {
        List<Medico> medici = controller.recuperaTuttiMedici();
        if (medici != null) {
            for (Medico m : medici) {
                cmbMediciAssenti.addItem(m.getMatricola() + " - " + m.getUsername());
            }
        }
    }

    /**
     * @return finestra del pannello ricerca sostituti
     */
    public JFrame getFrame() {
        return frame;
    }
}