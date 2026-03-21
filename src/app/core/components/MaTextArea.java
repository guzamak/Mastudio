/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.core.components;

/**
 *
 * @author poke
 */
import app.core.components.fonts.IBMPlexSansThaiFont;
import javax.swing.*;
import java.awt.*;
import javax.swing.text.DefaultCaret;

public class MaTextArea extends JTextArea {

    private int borderSize = 0;
    private int arc = 0;
    private Color borderColor = Color.BLACK;

    public MaTextArea() {
        setDefaultStyle();
    }

    private void setDefaultStyle() {
        setFont(IBMPlexSansThaiFont.regular(14f));
        setForeground(Color.BLACK);
        setOpaque(true);
        setLineWrap(true);        // better for textarea
        setWrapStyleWord(true);   // wrap by words
//        make it not scroll to end auto when sstat
        DefaultCaret caret = (DefaultCaret) this.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
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
    protected void paintComponent(Graphics g) {
        if (arc > 0) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

            g2.dispose();
        }

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {

        if (borderSize > 0) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderSize));

            if (arc > 0) {
                g2.drawRoundRect(
                        borderSize / 2,
                        borderSize / 2,
                        getWidth() - borderSize,
                        getHeight() - borderSize,
                        arc,
                        arc
                );
            } else {
                g2.drawRect(
                        borderSize / 2,
                        borderSize / 2,
                        getWidth() - borderSize,
                        getHeight() - borderSize
                );
            }

            g2.dispose();
        }
    }
}