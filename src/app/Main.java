/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import app.core.components.MaScrollBar;
import app.model.session.SessionManager;
import app.presentation.auth.view.SignInFrame;
import app.presentation.dashboard.view.DashboardFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        UIManager.put("ScrollBarUI", MaScrollBar.class.getName());

        SwingUtilities.invokeLater(() -> {
            if (SessionManager.hasSession()) {
                new DashboardFrame().setVisible(true);
            } else {
                new SignInFrame().setVisible(true);
            }
        });
    }
}