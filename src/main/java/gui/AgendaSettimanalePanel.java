package gui;

import controller.Controller;
import model.Prestazione;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Pannello che mostra al medico le prestazioni programmate per i prossimi 7 giorni.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class AgendaSettimanalePanel {
    private JPanel mainPanel;
    private JTable tblSettimanale;
    private JButton btnChiudi;

    private JFrame frame;
    private JFrame frameChiamante;
    private Controller controller;
    private DefaultTableModel tableModel;

    /**
     * Costruisce il pannello agenda settimanale e carica le prestazioni della settimana.
     *
     * @param controller     coordinatore centrale del sistema
     * @param frameChiamante finestra del menu medico da ripristinare alla chiusura
     */
    public AgendaSettimanalePanel(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

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
        tableModel.addColumn("ID Prestazione");
        tableModel.addColumn("Data");
        tableModel.addColumn("Ora Inizio");
        tableModel.addColumn("Ora Fine");
        tableModel.addColumn("Tipo Visita");
        tableModel.addColumn("ID Ricovero");
        tableModel.addColumn("Esito / Referto");
        tblSettimanale.setModel(tableModel);
    }

    // Recupera le prestazioni settimanali dal controller e popola la tabella
    private void caricaDati() {
        tableModel.setRowCount(0);
        List<Prestazione> lista = controller.agendaSettimanale();

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nessuna prestazione programmata per i prossimi 7 giorni.", "Agenda Settimanale", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Prestazione p : lista) {
                int idRicovero = (p.getRicovero() != null) ? p.getRicovero().getIdRicovero() : 0;
                // Usa il campo data diretto della prestazione invece della data del ricovero
                Object dataVisita = (p.getData() != null) ? p.getData() : "N.D.";
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

    /**
     * @return finestra del pannello agenda settimanale
     */
    public JFrame getFrame() {
        return frame;
    }
}