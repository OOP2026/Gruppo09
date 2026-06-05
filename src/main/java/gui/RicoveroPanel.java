package gui;

import controller.Controller;
import model.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RicoveroPanel {
    // Casella ID rimossa dai componenti per allinearsi al file .form
    private JPanel mainPanel;
    private JComboBox<String> cmbPazienti;
    private JComboBox<Integer> cmbLetti;
    private JTextField txtDataInizio;
    private JTextField txtOraInizio;
    private JTextField txtDataFine;
    private JTextField txtOraFine;
    private JButton btnSalva;
    private JButton btnAnnulla;

    private JFrame frame;
    private JFrame frameChiamante;
    private Controller controller;

    public RicoveroPanel(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        this.frame = new JFrame("Registrazione Nuovo Ricovero");
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);

        popolaMenuTendina();

        btnSalva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Lettura dei soli campi temporali rimasti nella form
                    String dataInizioRaw = txtDataInizio.getText().trim();
                    String oraInizioRaw = txtOraInizio.getText().trim();
                    String dataFineRaw = txtDataFine.getText().trim();
                    String oraFineRaw = txtOraFine.getText().trim();

                    // Validazione dei campi vuoti
                    if (dataInizioRaw.isEmpty() || oraInizioRaw.isEmpty() || dataFineRaw.isEmpty() || oraFineRaw.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Tutti i campi devono essere compilati!",
                                "Errore di Validazione", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String cfSelezionato = (String) cmbPazienti.getSelectedItem();
                    Integer idLettoSelezionato = (Integer) cmbLetti.getSelectedItem();

                    if (cfSelezionato == null || idLettoSelezionato == null) {
                        JOptionPane.showMessageDialog(frame, "Seleziona un paziente e un letto validi!",
                                "Dati Mancanti", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Parsing sicuro delle date e ore
                    LocalDate dataInizio = LocalDate.parse(dataInizioRaw);
                    LocalTime oraInizio = LocalTime.parse(oraInizioRaw);
                    LocalDate dataDimissionePrevista = LocalDate.parse(dataFineRaw);
                    LocalTime oraDimissionePrevista = LocalTime.parse(oraFineRaw);

                    if (dataDimissionePrevista.isBefore(dataInizio)) {
                        JOptionPane.showMessageDialog(frame, "La data di fine non può essere precedente a quella di inizio!",
                                "Incongruenza Temporale", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Istanziamo gli oggetti del modello passando 0 come ID finto (ci pensa il DB SERIAL)
                    Paziente pazienteTemporaneo = new Paziente(cfSelezionato, "", "");
                    Letto lettoTemporaneo = new Letto(idLettoSelezionato);
                    Ricovero nuovoRicovero = new Ricovero(0, pazienteTemporaneo, lettoTemporaneo, dataInizio, oraInizio, dataDimissionePrevista, oraDimissionePrevista);

                    // Inoltro dell'operazione al controller
                    boolean esito = controller.gestisciRicoveri(nuovoRicovero);

                    if (esito) {
                        JOptionPane.showMessageDialog(frame, "Ricovero registrato con successo!",
                                "Operazione Completata", JOptionPane.INFORMATION_MESSAGE);
                        chiudiETorna();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Il posto letto selezionato è già occupato nel periodo indicato.",
                                "Posto Letto Occupato", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (java.time.format.DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Formato data o ora non valido!\nUsa AAAA-MM-GG per le date e HH:MM per le ore.",
                            "Errore Formato", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Errore imprevisto: " + ex.getMessage(),
                            "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chiudiETorna();
            }
        });
    }

    private void popolaMenuTendina() {
        List<Paziente> listaPazienti = controller.recuperaTuttiPazienti();
        if (listaPazienti != null) {
            for (Paziente p : listaPazienti) {
                cmbPazienti.addItem(p.getCodiceFiscale());
            }
        }

        List<Letto> listaLetti = controller.recuperaTuttiLetti();
        if (listaLetti != null) {
            for (Letto l : listaLetti) {
                cmbLetti.addItem(l.getID_letto());
            }
        }
    }

    private void chiudiETorna() {
        frameChiamante.setVisible(true);
        frame.setVisible(false);
        frame.dispose();
    }

    public JFrame getFrame() {
        return frame;
    }
}