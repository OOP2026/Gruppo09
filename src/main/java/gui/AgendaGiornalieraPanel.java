package gui;

import controller.Controller;
import model.Prestazione;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AgendaGiornalieraPanel {
    private JPanel mainPanel;
    private JTable tblAgenda;
    private JButton btnChiudi;

    private JFrame frame;
    private JFrame frameChiamante;
    private Controller controller;
    private DefaultTableModel tableModel;

    // Il costruttore accetta solo i riferimenti di controllo, rispettando l'isolamento dei dati
    public AgendaGiornalieraPanel(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        // Configurazione della cornice visiva per l'agenda
        this.frame = new JFrame("Agenda Giornaliera Medico");
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inizializzazione della tabella e caricamento dei record
        configuraTabella();
        caricaDati();

        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);

        // Gestione del click sul pulsante Chiudi
        btnChiudi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ripristino del menu principale del medico e distruzione della finestra corrente
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose(); // Libera la memoria RAM occupata dal frame
            }
        });
    }

    private void configuraTabella() {
        // Configura il modello in modo da rendere le celle non modificabili direttamente
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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

        // Recupera le degenze odierne sfruttando lo stato della sessione interna del controller
        List<Prestazione> lista = controller.agendaGiornaliera();

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nessuna prestazione in agenda per oggi.", "Agenda", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Prestazione p : lista) {
                // Associazione corretta tramite il metodo camelCase della classe Ricovero
                int idRicovero = (p.getRicovero() != null) ? p.getRicovero().getIdRicovero() : 0;
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