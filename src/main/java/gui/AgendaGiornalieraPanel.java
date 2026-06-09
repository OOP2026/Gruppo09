package gui;

import controller.Controller;
import model.Prestazione;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Pannello che mostra al medico le prestazioni programmate per la giornata odierna.
 * Permette anche di modificare l'esito di una prestazione selezionata.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class AgendaGiornalieraPanel {
    private JPanel mainPanel;
    private JTable tblAgenda;
    private JButton btnModificaEsito;
    private JButton btnChiudi;

    private JFrame frame;
    private JFrame frameChiamante;
    private Controller controller;
    private DefaultTableModel tableModel;
    private List<Prestazione> listaPrestazioni;

    /**
     * Costruisce il pannello agenda giornaliera e carica le prestazioni di oggi.
     *
     * @param controller     coordinatore centrale del sistema
     * @param frameChiamante finestra del menu medico da ripristinare alla chiusura
     */
    public AgendaGiornalieraPanel(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        this.frame = new JFrame("Agenda Giornaliera Medico");
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        configuraTabella();
        caricaDati();

        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);

        // Permette di modificare l'esito della prestazione selezionata in tabella
        btnModificaEsito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tblAgenda.getSelectedRow();
                if (rigaSelezionata < 0) {
                    JOptionPane.showMessageDialog(frame, "Seleziona una prestazione dalla tabella.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Prestazione selezionata = listaPrestazioni.get(rigaSelezionata);
                String nuovoEsito = JOptionPane.showInputDialog(frame,
                        "Inserisci l'esito della prestazione:",
                        selezionata.getEsito());

                if (nuovoEsito != null && !nuovoEsito.trim().isEmpty()) {
                    boolean ok = controller.aggiornaEsitoPrestazione(
                            selezionata.getIdPrestazione(), nuovoEsito.trim());
                    if (ok) {
                        JOptionPane.showMessageDialog(frame, "Esito aggiornato con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        caricaDati();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Errore durante l'aggiornamento.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
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
        tableModel.addColumn("ID Prestazione");
        tableModel.addColumn("Tipo Visita");
        tableModel.addColumn("Ora Inizio");
        tableModel.addColumn("Ora Fine");
        tableModel.addColumn("ID Ricovero");
        tableModel.addColumn("Esito / Referto");
        tblAgenda.setModel(tableModel);
    }

    // Recupera le prestazioni di oggi dal controller e popola la tabella
    private void caricaDati() {
        tableModel.setRowCount(0);
        listaPrestazioni = controller.agendaGiornaliera();

        if (listaPrestazioni.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nessuna prestazione in agenda per oggi.", "Agenda", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Prestazione p : listaPrestazioni) {
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

    /**
     * @return finestra del pannello agenda giornaliera
     */
    public JFrame getFrame() {
        return frame;
    }
}