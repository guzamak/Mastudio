/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author poke
 */
import app.components.lib.MaScrollBar;
import app.lib.SessionManager;
import app.ui.DashboardFrame;
import app.ui.SignInFrame;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            if (SessionManager.hasSession()) {
//                make it root frame when close other it will not close if 
// not close root
               DashboardFrame d =  new DashboardFrame();
               d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               d.setVisible(true);
        } else {
                new SignInFrame().setVisible(true);
            }
        });
    }
}
