package gui;

import controller.Controller;
import model.Letto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Pannello per la visualizzazione dei letti di un reparto con il loro stato di occupazione.
 * I letti occupati vengono mostrati in rosso come richiesto dalla traccia.
 * Mostra anche il conteggio dei ricoveri attivi nel titolo della finestra.
 * Accessibile solo agli amministratori.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class LettiRepartoPanel {

    private JPanel mainPanel;
    private JTextField txtIdReparto;
    private JButton btnCerca;
    private JTable tblLetti;
    private JButton btnChiudi;

    private JFrame frame;
    private JFrame frameChiamante;
    private Controller controller;
    private DefaultTableModel tableModel;

    /**
     * Costruisce il pannello letti reparto e configura la tabella con il renderer per i colori.
     *
     * @param controller     coordinatore centrale del sistema
     * @param frameChiamante finestra della dashboard admin da ripristinare alla chiusura
     */
    public LettiRepartoPanel(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        this.frame = new JFrame("Disponibilità Letti per Reparto");
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        configuraTabella();

        this.frame.pack();
        // Dimensione minima per garantire che tutti i componenti siano visibili
        this.frame.setMinimumSize(new Dimension(400, 400));
        this.frame.setLocationRelativeTo(frameChiamante);

        btnCerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idRaw = txtIdReparto.getText().trim();
                    if (idRaw.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Inserisci l'ID del reparto.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    int idReparto = Integer.parseInt(idRaw);
                    caricaLetti(idReparto);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "L'ID reparto deve essere un numero.", "Errore Formato", JOptionPane.ERROR_MESSAGE);
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

    // Configura le colonne e il renderer che colora in rosso i letti occupati
    private void configuraTabella() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Codice Letto");
        tableModel.addColumn("Stato");
        tblLetti.setModel(tableModel);

        // I letti occupati vengono mostrati in rosso come richiesto dalla traccia
        tblLetti.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String stato = (String) table.getModel().getValueAt(row, 1);
                if ("OCCUPATO".equals(stato)) {
                    c.setForeground(Color.RED);
                } else {
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });
    }

    // Recupera i letti del reparto e aggiorna il titolo con il conteggio ricoveri attivi
    private void caricaLetti(int idReparto) {
        tableModel.setRowCount(0);
        List<Letto> lista = controller.getLettiPerReparto(idReparto);
        int ricoveriAttivi = controller.contaRicoveriAttivi(idReparto);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Nessun letto trovato per il reparto " + idReparto,
                    "Nessun risultato", JOptionPane.INFORMATION_MESSAGE);
        } else {
            frame.setTitle("Letti Reparto " + idReparto +
                    " — Ricoveri attivi: " + ricoveriAttivi);

            for (Letto l : lista) {
                tableModel.addRow(new Object[]{
                        l.getIdLetto(),
                        l.isOccupato() ? "OCCUPATO" : "LIBERO"
                });
            }

            frame.revalidate();
            frame.repaint();
        }
    }

    /**
     * @return finestra del pannello letti reparto
     */
    public JFrame getFrame() {
        return frame;
    }
}