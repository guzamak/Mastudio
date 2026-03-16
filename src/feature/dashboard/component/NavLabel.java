/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package feature.dashboard.component;

import core.component.font.IBMPlexSansThaiFont;
import core.component.MaLabel;
import core.component.Macolor;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author poke
 */
public class NavLabel extends JPanel {

    private MaLabel ma, studio, extra;

    public NavLabel() {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setBackground(Macolor.seablue);

        ma = new MaLabel();
        ma.setText("./ Ma");
        ma.setTextColor(Macolor.magreen);
        ma.setFont(IBMPlexSansThaiFont.bold(36));
//        ma.setBorderSize(1);
//        ma.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        studio = new MaLabel();
        studio.setText(" Studio");
        studio.setTextColor(Macolor.magreen);
        studio.setFont(IBMPlexSansThaiFont.bold(24));
//        studio.setBorderSize(1);
//        studio.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 0;

        gbc.gridx = 0;
        add(ma, gbc);

        gbc.gridx = 1;
        add(studio, gbc);

        extra = new MaLabel();
        extra.setTextColor(Macolor.magreen);
        extra.setFont(IBMPlexSansThaiFont.thin(14));
    }

    public void setExtraText(String extraText) {

        extra.setText(" | " + extraText);

        if (extra.getParent() == null) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridy = 0;
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.fill = GridBagConstraints.VERTICAL;
            add(extra, gbc);
        }

        revalidate();
        repaint();
    }

}
