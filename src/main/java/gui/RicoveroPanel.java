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

        // Configurazione della finestra per la registrazione del ricovero
        this.frame = new JFrame("Registrazione Nuovo Ricovero");
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setLocationRelativeTo(frameChiamante);

        // Caricamento dei dati iniziali nei menu a tendina
        popolaMenuTendina();

        // Gestione del click sul pulsante Salva
        btnSalva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String dataInizioRaw = txtDataInizio.getText().trim();
                    String oraInizioRaw = txtOraInizio.getText().trim();
                    String dataFineRaw = txtDataFine.getText().trim();
                    String oraFineRaw = txtOraFine.getText().trim();

                    // Validazione locale superficiale sulla presenza dei dati
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

                    // Conversione delle stringhe nei rispettivi tipi temporali
                    LocalDate dataInizio = LocalDate.parse(dataInizioRaw);
                    LocalTime oraInizio = LocalTime.parse(oraInizioRaw);
                    LocalDate dataDimissionePrevista = LocalDate.parse(dataFineRaw);
                    LocalTime oraDimissionePrevista = LocalTime.parse(oraFineRaw);

                    // Controllo logico di congruenza temporale delle date inserite
                    if (dataDimissionePrevista.isBefore(dataInizio)) {
                        JOptionPane.showMessageDialog(frame, "La data di fine non può essere precedente a quella di inizio!",
                                "Incongruenza Temporale", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Istanziazione dell'entità temporanea da trasmettere al controller
                    Paziente pazienteTemporaneo = new Paziente(cfSelezionato, "", "");
                    Letto lettoTemporaneo = new Letto(idLettoSelezionato);
                    Ricovero nuovoRicovero = new Ricovero(0, pazienteTemporaneo, lettoTemporaneo, dataInizio, oraInizio, dataDimissionePrevista, oraDimissionePrevista);

                    // Esecuzione dell'operazione di business tramite il controller
                    boolean esito = controller.gestisciRicoveri(nuovoRicovero);

                    if (esito) {
                        JOptionPane.showMessageDialog(frame, "Ricovero registraro con successo!",
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

        // Gestione del click sul pulsante Annulla
        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chiudiETorna();
            }
        });
    }

    private void popolaMenuTendina() {
        // Popolamento della JComboBox dei pazienti recuperati dal database
        List<Paziente> listaPazienti = controller.recuperaTuttiPazienti();
        if (listaPazienti != null) {
            for (Paziente p : listaPazienti) {
                cmbPazienti.addItem(p.getCodiceFiscale());
            }
        }

        // Popolamento della JComboBox dei letti (corretto con il nuovo metodo camelCase)
        List<Letto> listaLetti = controller.recuperaTuttiLetti();
        if (listaLetti != null) {
            for (Letto l : listaLetti) {
                cmbLetti.addItem(l.getIdLetto());
            }
        }
    }

    private void chiudiETorna() {
        // Ripristino della dashboard chiamante e rilascio delle risorse correnti
        frameChiamante.setVisible(true);
        frame.setVisible(false);
        frame.dispose();
    }

    public JFrame getFrame() {
        return frame;
    }
}