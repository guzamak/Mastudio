/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.core.components;

import javax.swing.*;

import app.core.components.fonts.IBMPlexSansThaiFont;

import java.awt.*;
import java.awt.event.*;

public class MaOptionPane implements ActionListener {

    private JDialog dialog;
    private MaButton okBtn;

    public MaOptionPane(Component parent, String message) {

        dialog = new JDialog(
                SwingUtilities.getWindowAncestor(parent),
                "Message",
                Dialog.ModalityType.APPLICATION_MODAL
        );

        dialog.setUndecorated(true);

//        custom JPanel  jaa
        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2.setColor(new Color(200, 200, 200));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.dispose();
            }
        };

        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(message, JLabel.CENTER);
        label.setFont(IBMPlexSansThaiFont.medium(14f));

        okBtn = new MaButton();
        okBtn.setText("รับทราบ");
        okBtn.setFont(IBMPlexSansThaiFont.medium(13f));
        okBtn.setButtonColor(Macolor.magreen);

        okBtn.addActionListener(this);

        panel.add(label, BorderLayout.CENTER);
        panel.add(okBtn, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setSize(280, 140);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == okBtn) {
            dialog.dispose();
        }
    }

    // helper method to keep same usage style
    public static void showMessageDialog(Component parent, String message) {
        new MaOptionPane(parent, message);
    }
}