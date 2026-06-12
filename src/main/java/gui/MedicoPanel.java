package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dashboard principale per gli utenti con ruolo Medico.
 * Fornisce l'accesso alle funzionalità di agenda e registrazione prestazioni.
 *
 * @author Enrico Muselli, Ferdinando Longobardo, Francesco Megna
 * @version 1.0
 */
public class MedicoPanel {

    private JPanel mainPanel;
    private JButton btnAgendaGiornaliera;
    private JButton btnAgendaSettimanale;
    private JButton btnRegistraPrestazione;
    private JButton btnLogout;

    private Controller controller;
    private JFrame frame;
    private JFrame frameChiamante;

    /**
     * Costruisce la dashboard medico e registra tutti i listener.
     *
     * @param controller     coordinatore centrale del sistema
     * @param frame          finestra corrente della dashboard
     * @param frameChiamante finestra di login da ripristinare al logout
     */
    public MedicoPanel(Controller controller, JFrame frame, JFrame frameChiamante) {
        this.controller = controller;
        this.frame = frame;
        this.frameChiamante = frameChiamante;

        // Apertura della schermata agenda giornaliera
        btnAgendaGiornaliera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgendaGiornalieraPanel agendaPanel = new AgendaGiornalieraPanel(controller, frame);
                agendaPanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Apertura della schermata agenda settimanale
        btnAgendaSettimanale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgendaSettimanalePanel settimanalePanel = new AgendaSettimanalePanel(controller, frame);
                settimanalePanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Apertura della schermata di registrazione prestazioni
        btnRegistraPrestazione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistraPrestazionePanel registraPanel = new RegistraPrestazionePanel(controller, frame);
                registraPanel.getFrame().setVisible(true);
                frame.setVisible(false);
            }
        });

        // Logout: azzera la sessione, ripristina il login e dealloca la finestra
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.logout();
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

    /**
     * @return pannello radice della dashboard medico
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}