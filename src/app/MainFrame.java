/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import app.core.components.MaFrame;
import app.core.components.MaInternalFrame;
import app.core.components.Macolor;
import app.core.components.fonts.IBMPlexSansThaiFont;
import javax.swing.JFrame;

/**
 *
 * @author poke
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import model.session.SessionManager;
import presentation.auth.view.SignInFrame;
import presentation.dashboard.view.DashboardFrame;

public class MainFrame extends MaFrame {

    private JDesktopPane desktopPane;
    private static MainFrame instance;
    public static ArrayList<JInternalFrame> frames = new ArrayList<>();

    public MainFrame() {
        MenuBar menu = new MenuBar();
        setJMenuBar(menu);
        instance = this;
        setTitle("Mastudio");
        setSize(1440,1080);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);
    }

    public void openInternalFrame(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
        frame.toFront();
        frame.setLocation(0, 0);
        frames.add(frame);
    }
    

    public static MainFrame getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
            if (SessionManager.hasSession()) {
                MainFrame.instance.openInternalFrame(new TimeNotificationFrame());
                DashboardFrame d = new DashboardFrame();
                MainFrame.instance.openInternalFrame(d);
            } else {
                SignInFrame s = new SignInFrame();
                MainFrame.instance.openInternalFrame(s);
            }
        });
    }
}
