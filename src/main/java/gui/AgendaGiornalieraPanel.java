package gui;

import controller.Controller;
import model.Prestazione;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AgendaGiornalieraPanel {
    // Componenti legati al file .form tramite IntelliJ Designer
    private JPanel mainPanel;
    private JTable tblAgenda;
    private JButton btnChiudi;

    private JFrame frame;
    private JFrame frameChiamante;
    private Controller controller;
    private DefaultTableModel tableModel;
    private String matricolaMedico;

    public AgendaGiornalieraPanel(Controller controller, JFrame frameChiamante, String matricolaMedico) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;
        this.matricolaMedico = matricolaMedico;

        this.frame = new JFrame("Agenda Giornaliera Medico");
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
                return false; // Impedisce la modifica delle celle direttamente dalla tabella
            }
        };
        tableModel.addColumn("ID Prestazione");
        tableModel.addColumn("Tipo Visita");
        tableModel.addColumn("Ora Inizio");
        tableModel.addColumn("Ora Fine");
        tableModel.addColumn("ID Ricovero");
        tableModel.addColumn("Esito / Referto");
        tblAgenda.setModel(tableModel);
    }

    private void caricaDati() {
        tableModel.setRowCount(0);
        List<Prestazione> lista = controller.recuperaAgendaGiornaliera(matricolaMedico);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nessuna prestazione in agenda per oggi.", "Agenda", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Prestazione p : lista) {
                // Se nel tuo modello Ricovero il metodo dell'ID ha un nome diverso (es. getIdRicovero), nominalo di conseguenza
                int idRicovero = (p.getRicovero() != null) ? p.getRicovero().getID_ricovero() : 0;
                tableModel.addRow(new Object[]{
                        p.getIdPrestazione(),
                        p.getTipo(),
                        p.getOraInizio(),
                        p.getOraFine(),
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