/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package feature.auth.presentation;

/**
 *
 * @author poke
 */
import core.component.MaOptionPane;
import core.component.MaTextField;
import core.component.MaLabel;
import core.component.MaFrame;
import core.component.MaPasswordField;
import core.component.MaButton;
import core.component.Macolor;
import core.component.font.IBMPlexSansThaiFont;
import feature.dashboard.presentation.DashboardFrameExample;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInFrameExample extends MaFrame implements ActionListener {

    private MaTextField username;
    private MaPasswordField password;
    private MaButton loginBtn;

    public SignInFrameExample() {

        setTitle("Sign In");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Macolor.seablue);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        int row = 0;

        MaLabel headerLabel = new MaLabel();
        headerLabel.setText("./ MA studio");
        headerLabel.setTextColor(Macolor.magreen);
        headerLabel.setFont(IBMPlexSansThaiFont.bold(36f));

        gbc.gridy = row++;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(headerLabel, gbc);

        MaLabel descLabel = new MaLabel();
        descLabel.setText("โปรดเข้าสู่ระบบเพื่อใช้งาน.");
        descLabel.setTextColor(Macolor.textgrey);

        gbc.gridy = row++;
        panel.add(descLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;

        MaLabel userLabel = new MaLabel();
        userLabel.setText("ชื่อผู้ใช้ (username)");
        userLabel.setFont(IBMPlexSansThaiFont.medium(14f));

        gbc.gridy = row++;
        panel.add(userLabel, gbc);

        username = new MaTextField();
        username.setArc(20);
        username.setPadding(10);
        username.setColumns(20);
        username.setPreferredSize(new Dimension(220, 40));
        username.setFieldColor(Macolor.bggrey);
        username.setBorderSize(1);
        username.setBorderColor(Macolor.textgrey);

        gbc.gridy = row++;
        panel.add(username, gbc);

        MaLabel passLabel = new MaLabel();
        passLabel.setText("รหัสผ่าน ( password )");
        passLabel.setFont(IBMPlexSansThaiFont.medium(14f));

        gbc.gridy = row++;
        panel.add(passLabel, gbc);

        password = new MaPasswordField();
        password.setArc(20);
        password.setPadding(10);
        password.setColumns(20);
        password.setPreferredSize(new Dimension(220, 40));
        password.setFieldColor(Macolor.bggrey);
        password.setBorderSize(1);
        password.setBorderColor(Macolor.textgrey);

        gbc.gridy = row++;
        panel.add(password, gbc);

        loginBtn = new MaButton();
        loginBtn.setText("เข้าสู่ระบบ");
        loginBtn.setArc(20);
        loginBtn.setButtonColor(Macolor.magreen);
        loginBtn.setPreferredSize(new Dimension(220, 40));

        gbc.gridy = row++;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginBtn, gbc);

        add(panel, BorderLayout.CENTER);

        loginBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginBtn) {

            String user = username.getText();
            String pass = new String(password.getPassword());

            if (user.equals("admin") && pass.equals("1234")) {

                MaOptionPane.showMessageDialog(this, "การเข้าสู่ระบบสำเร็จ");

                dispose();
                new DashboardFrameExample().setVisible(true);

            } else {

                MaOptionPane.showMessageDialog(this, "รหัสผ่านหรือชื่อผู้ใช้ไม่ถูกต้อง");

            }
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new SignInFrameExample().setVisible(true));
    }
}