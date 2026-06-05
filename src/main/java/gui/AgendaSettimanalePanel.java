package gui;

import controller.Controller;
import model.Prestazione;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AgendaSettimanalePanel {
    private JPanel mainPanel;
    private JTable tblSettimanale;
    private JButton btnChiudi;

    private JFrame frame;
    private JFrame frameChiamante;
    private Controller controller;
    private DefaultTableModel tableModel;
    private String usernameMedico;

    public AgendaSettimanalePanel(Controller controller, JFrame frameChiamante, String usernameMedico) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;
        this.usernameMedico = usernameMedico;

        this.frame = new JFrame("Pianificazione Settimanale");
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        configuraTabella();
        caricaDati();

        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);

        btnChiudi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
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
        tableModel.addColumn("ID Prestazione");
        tableModel.addColumn("Data");
        tableModel.addColumn("Ora Inizio");
        tableModel.addColumn("Ora Fine");
        tableModel.addColumn("Tipo Visita");
        tableModel.addColumn("ID Ricovero");
        tableModel.addColumn("Esito / Referto");
        tblSettimanale.setModel(tableModel);
    }

    private void caricaDati() {
        tableModel.setRowCount(0);
        List<Prestazione> lista = controller.recuperaAgendaSettimanale(usernameMedico);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nessuna prestazione programmata per i prossimi 7 giorni.", "Agenda Settimanale", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Prestazione p : lista) {
                int idRicovero = (p.getRicovero() != null) ? p.getRicovero().getID_ricovero() : 0;

                // Recuperiamo la data dal ricovero
                Object dataVisita = (p.getRicovero() != null) ? p.getRicovero().getDataInizio() : "N.D.";

                tableModel.addRow(new Object[]{
                        p.getIdPrestazione(),
                        dataVisita,
                        p.getOraInizio(),
                        p.getOraFine(),
                        p.getTipo(),
                        idRicovero,
                        p.getEsito() != null ? p.getEsito() : "Da refertare"
                });
            }
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}