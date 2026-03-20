/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.core.components;

import javax.swing.*;

import app.core.components.fonts.IBMPlexSansThaiFont;

import java.awt.*;

public class MaLabel extends JLabel {

    private int borderSize = 0;
    private int arc = 0;
    private Color borderColor = Color.BLACK;

    public MaLabel() {
        setDefaultStyle();
    }

    private void setDefaultStyle() {
        setFont(IBMPlexSansThaiFont.regular(14f));
        setForeground(Color.BLACK);
        setOpaque(false);
    }

    public void setFontSize(Font font) {
        setFont(font);
    }

    public void setFontName(String name) {
        setFont(new Font(name, Font.PLAIN, getFont().getSize()));
    }

    public void setTextColor(Color color) {
        setForeground(color);
    }

    public void setBorderSize(int size) {
        borderSize = size;
        repaint();
    }

    public void setBorderArc(int arc) {
        this.arc = arc;
        repaint();
    }

    public void setBorderColor(Color color) {
        borderColor = color;
        repaint();
    }

    @Override
    protected void paintBorder(Graphics g) {

        if (borderSize > 0) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderSize));

            if (arc > 0) {
                g2.drawRoundRect(borderSize / 2,borderSize / 2,getWidth() - borderSize,getHeight() - borderSize, arc,arc
                );
            } else {
                g2.drawRect(borderSize / 2,borderSize / 2,getWidth() - borderSize,getHeight() - borderSize
                );
            }

            g2.dispose();
        }
    }
}
