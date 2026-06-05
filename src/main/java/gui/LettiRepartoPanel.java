package gui;

import controller.Controller;
import model.Letto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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

    public LettiRepartoPanel(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        this.frame = new JFrame("Disponibilità Letti per Reparto");
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        configuraTabella();

        this.frame.pack();
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

    private void caricaLetti(int idReparto) {
        tableModel.setRowCount(0);
        List<Letto> lista = controller.getLettiPerReparto(idReparto);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Nessun letto trovato per il reparto " + idReparto,
                    "Nessun risultato", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Letto l : lista) {
                tableModel.addRow(new Object[]{
                        l.getIdLetto(),
                        l.isOccupato() ? "OCCUPATO" : "LIBERO"
                });
            }
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}