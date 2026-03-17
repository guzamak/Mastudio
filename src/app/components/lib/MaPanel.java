/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.components.lib;

import app.components.fonts.IBMPlexSansThaiFont;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author poke
 */
public class MaPanel extends JPanel {

    private boolean round = true;
    private int arc = 20;
    private int borderSize = 1;

    private Color borderColor = new Color(180, 180, 180);
    private Color backgroundColor = Macolor.seablue;

    // padding (margin not work now)
    private int padTop = 0;
    private int padLeft = 0;
    private int padBottom = 0;
    private int padRight = 0;
    
    public MaPanel() {
        setBackground(backgroundColor);
        setDefaultStyle();
    }
    
    private void setDefaultStyle() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(padTop, padLeft, padBottom, padRight));
        updateSpace();
    }
    
    public void setRound(boolean round) {
        this.round = round;
        repaint();
    }

    public boolean isRound() {
        return round;
    }

    public void setArc(int arc) {
        this.arc = arc;
        repaint();
    }

    public void setBorderSize(int size) {
        this.borderSize = size;
        repaint();
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    public void setFieldColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    public void setTextColor(Color color) {
        setForeground(color);
    }

    public void setFontSize(int size) {
        setFont(getFont().deriveFont((float) size));
    }

    public void setFontName(String name) {
        setFont(new Font(name, Font.PLAIN, getFont().getSize()));
    }


    private void updateSpace() {
        Border padding = BorderFactory.createEmptyBorder(padTop, padLeft, padBottom, padRight);
        setBorder(padding);
    }

    public void setPadding(int p) {
        this.padTop = p;
        this.padLeft = p;
        this.padBottom = p;
        this.padRight = p;
        updateSpace();
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(backgroundColor);

        if (round) {
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        } else {
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderSize));

        if (round) {
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        } else {
            g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }

        g2.dispose();
    }
}