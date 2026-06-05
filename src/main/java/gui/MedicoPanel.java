package gui;

import controller.Controller;
import model.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class MedicoPanel {

    private JPanel mainPanel;
    private JButton btnAgendaGiornaliera;
    private JButton btnAgendaSettimanale;
    private JButton btnRegistraPrestazione;
    private JButton btnLogout;

    private Controller controller;
    private JFrame frame;            // La finestra corrente (Area Medica)
    private JFrame frameChiamante;   // La finestra di Login per consentire il ritorno al logout

    public MedicoPanel(Controller controller, JFrame frame, JFrame frameChiamante, String matricolaMedico) {
        this.controller = controller;
        this.frame = frame;
        this.frameChiamante = frameChiamante;

        // Gestione della visualizzazione dell'agenda giornaliera delle prestazioni
        btnAgendaGiornaliera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Istanziamo il nuovo pannello dell'agenda passando controller, il frame attuale e la matricola
                AgendaGiornalieraPanel agendaPanel = new AgendaGiornalieraPanel(controller, frame, matricolaMedico);

                // 2. Rendiamo visibile la JTable dell'agenda
                agendaPanel.getFrame().setVisible(true);

                // 3. Nascondiamo momentaneamente il menu principale del medico
                frame.setVisible(false);
            }
        });

        // Gestione della visualizzazione dell'agenda settimanale delle prestazioni
        btnAgendaSettimanale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Passiamo lo username del medico (che abbiamo verificato arrivi correttamente dal login)
                AgendaSettimanalePanel settimanalePanel = new AgendaSettimanalePanel(controller, frame, matricolaMedico);
                settimanalePanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Gestione della registrazione di una nuova prestazione medica sul paziente
        btnRegistraPrestazione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistraPrestazionePanel registraPanel = new RegistraPrestazionePanel(controller, frame, matricolaMedico);
                registraPanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Gestione della procedura di disconnessione (Logout)
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Resetta lo stato utente e cancella la sessione corrente all'interno del Controller
                controller.logout();

                // Ripristino coordinato della visibilità delle finestre
                frameChiamante.setVisible(true); // Rende nuovamente visibile la finestra di Login
                frame.setVisible(false);         // Nasconde l'area medica corrente
                frame.dispose();                 // Distrugge la finestra corrente per liberare risorse di memoria RAM
            }
        });
    }
    // Restituisce il pannello grafico principale per poterlo inserire all'interno della finestra (JFrame)
    public JPanel getMainPanel() {
        return mainPanel;
    }
}