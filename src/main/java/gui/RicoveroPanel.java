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

        // Il pack() va dopo il popolamento per calcolare le dimensioni corrette
        popolaMenuTendina();

        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);

        btnSalva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String dataInizioRaw = txtDataInizio.getText().trim();
                    String oraInizioRaw = txtOraInizio.getText().trim();
                    String dataFineRaw = txtDataFine.getText().trim();
                    String oraFineRaw = txtOraFine.getText().trim();

                    if (dataInizioRaw.isEmpty() || oraInizioRaw.isEmpty() ||
                            dataFineRaw.isEmpty() || oraFineRaw.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Tutti i campi devono essere compilati!", "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String cfSelezionato = (String) cmbPazienti.getSelectedItem();
                    Integer idLettoSelezionato = (Integer) cmbLetti.getSelectedItem();

                    if (cfSelezionato == null || idLettoSelezionato == null) {
                        JOptionPane.showMessageDialog(frame, "Seleziona un paziente e un letto validi!", "Dati Mancanti", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    LocalDate dataInizio = LocalDate.parse(dataInizioRaw);
                    LocalTime oraInizio = LocalTime.parse(oraInizioRaw);
                    LocalDate dataDimissionePrevista = LocalDate.parse(dataFineRaw);
                    LocalTime oraDimissionePrevista = LocalTime.parse(oraFineRaw);

                    if (dataDimissionePrevista.isBefore(dataInizio)) {
                        JOptionPane.showMessageDialog(frame, "La data di fine non può essere precedente a quella di inizio!", "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Costruisce un oggetto Ricovero minimo con i soli dati necessari al Controller
                    Paziente pazienteTemporaneo = new Paziente(cfSelezionato, "", "");
                    Letto lettoTemporaneo = new Letto(idLettoSelezionato);
                    Ricovero nuovoRicovero = new Ricovero(0, pazienteTemporaneo, lettoTemporaneo,
                            dataInizio, oraInizio, dataDimissionePrevista, oraDimissionePrevista);

                    boolean esito = controller.gestisciRicoveri(nuovoRicovero);

                    if (esito) {
                        JOptionPane.showMessageDialog(frame, "Ricovero registrato con successo!", "Operazione Completata", JOptionPane.INFORMATION_MESSAGE);
                        chiudiETorna();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Il letto selezionato è già occupato nel periodo indicato.", "Letto Occupato", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (java.time.format.DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Formato non valido.\nData: AAAA-MM-GG | Ora: HH:mm", "Errore Formato", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Errore imprevisto: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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
                cmbLetti.addItem(l.getIdLetto());
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