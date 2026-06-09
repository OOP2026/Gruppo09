package gui;

import controller.Controller;
import model.Paziente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Pannello per la visualizzazione dei pazienti in scadenza di dimissione.
 * Permette di cercare le dimissioni previste per oggi o per una data specifica.
 * Accessibile solo agli amministratori.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class DimissioniPanel {

    private JPanel mainPanel;
    private JButton btnOggi;
    private JTextField txtData;
    private JButton btnCerca;
    private JTable tblPazienti;
    private JButton btnChiudi;

    private JFrame frame;
    private JFrame frameChiamante;
    private Controller controller;
    private DefaultTableModel tableModel;

    /**
     * Costruisce il pannello dimissioni e configura la tabella dei risultati.
     *
     * @param controller     coordinatore centrale del sistema
     * @param frameChiamante finestra della dashboard admin da ripristinare alla chiusura
     */
    public DimissioniPanel(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        this.frame = new JFrame("Pazienti in Scadenza di Dimissione");
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        configuraTabella();

        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);

        // Carica i pazienti in scadenza nella giornata odierna
        btnOggi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caricaPazienti(LocalDate.now());
            }
        });

        // Carica i pazienti in scadenza nella data inserita dal campo di testo
        btnCerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String dataRaw = txtData.getText().trim();
                    if (dataRaw.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Inserisci una data valida.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    LocalDate data = LocalDate.parse(dataRaw);
                    caricaPazienti(data);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Formato data non valido. Usa AAAA-MM-GG.", "Errore Formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnChiudi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

    // Configura le colonne della tabella e disabilita la modifica diretta delle celle
    private void configuraTabella() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Codice Fiscale");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Cognome");
        tblPazienti.setModel(tableModel);
    }

    // Recupera i pazienti in scadenza per la data indicata e popola la tabella
    private void caricaPazienti(LocalDate data) {
        tableModel.setRowCount(0);
        List<Paziente> lista = controller.getPazientiInScadenza(data);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Nessun paziente in scadenza di dimissione per il " + data,
                    "Nessun risultato", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Paziente p : lista) {
                tableModel.addRow(new Object[]{
                        p.getCodiceFiscale(),
                        p.getNome(),
                        p.getCognome()
                });
            }
        }
    }

    /**
     * @return finestra del pannello dimissioni
     */
    public JFrame getFrame() {
        return frame;
    }
}