/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.presentation.dashboard.components;

import app.core.components.MaLabel;
import app.core.components.Macolor;
import app.core.components.fonts.IBMPlexSansThaiFont;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

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
