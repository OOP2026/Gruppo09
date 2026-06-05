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

    // Il costruttore riceve solo i riferimenti per il controllo visivo delle finestre
    public AgendaSettimanalePanel(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        // Configurazione della finestra per la pianificazione dei 7 giorni
        this.frame = new JFrame("Pianificazione Settimanale");
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inizializzazione colonne e caricamento elementi
        configuraTabella();
        caricaDati();

        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);

        // Gestione del click sul pulsante Chiudi
        btnChiudi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Riattiva il menu del medico e dealloca la finestra attuale
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

    private void configuraTabella() {
        // Rende la tabella non editabile digitando sopra le celle
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

        // Interroga il controller sfruttando la sessione memorizzata nel Control
        List<Prestazione> lista = controller.agendaSettimanale();

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nessuna prestazione programmata per i prossimi 7 giorni.", "Agenda Settimanale", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Prestazione p : lista) {
                // Recupero ID ricovero aggiornato secondo lo standard camelCase
                int idRicovero = (p.getRicovero() != null) ? p.getRicovero().getIdRicovero() : 0;

                // Estrazione della data di inizio della degenza associata
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