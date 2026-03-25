/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author poke
 */
import app.core.components.MaOptionPane;
import app.core.components.fonts.IBMPlexSansThaiFont;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.session.SessionManager;
import presentation.auth.view.SignInFrame;
import presentation.booking.view.BookingFrame;
import presentation.booking.view.BookingTableFrame;
import presentation.dashboard.view.DashboardFrame;

public class MenuBar extends JMenuBar implements ActionListener {

    private JMenu window;
    private JMenuItem signIn, dashboard, signout;

    public MenuBar() {
        window = new JMenu("menu");
        window.setFont(IBMPlexSansThaiFont.regular(14f));

        signIn = new JMenuItem("เข้าสู่ระบบ");
        dashboard = new JMenuItem("แดชบอร์ด");
        signout = new JMenuItem("ออกจากระบบ");

        signIn.setFont(IBMPlexSansThaiFont.regular(14f));
        dashboard.setFont(IBMPlexSansThaiFont.regular(14f));
        signout.setFont(IBMPlexSansThaiFont.regular(14f));

        signIn.addActionListener(this);
        dashboard.addActionListener(this);
        signout.addActionListener(this);

        window.add(signIn);
        window.add(dashboard);
        window.addSeparator();
        window.add(signout);

        add(window);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == signIn) {
            if (!SessionManager.getUsername().equals("")) {
                MaOptionPane.showMessageDialog(MainFrame.getInstance(), "ผู้ใช้เข้าสู่ระบบเรียบร้อยเเล้ว");
                return;
            }
            MainFrame.getInstance().openInternalFrame(new SignInFrame());

        } else if (source == dashboard) {
            if (SessionManager.getUsername().equals("")) {
                MaOptionPane.showMessageDialog(MainFrame.getInstance(), "โปรดเข้าสู่ระบบ");
                return;
            }
            MainFrame.getInstance().openInternalFrame(new DashboardFrame());
            
        } else if (source == signout) {
            if (SessionManager.getUsername().equals("")) {
                MaOptionPane.showMessageDialog(MainFrame.getInstance(), "ยังไม่ได้เข้าสู่ระบบจึงออกระบบไม่ได้");
                return;
            }
            SessionManager.clearSession();
            for (JInternalFrame frame : MainFrame.frames) {
                frame.dispose();
            }
            MainFrame.frames.clear();

            MainFrame.getInstance().openInternalFrame(new SignInFrame());
        }
    }
}
