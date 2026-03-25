/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.core.components;

import app.core.components.fonts.IBMPlexSansThaiFont;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.border.Border;

/**
 *
 * @author poke
 */
public class MaCheckBox extends JCheckBox {

    private int arc = 0;
    private int borderSize = 1;
    private int box_width = 20;
    private int box_and_text_space = 5;

    private int padTop = 0;
    private int padLeft = 0;
    private int padBottom = 0;
    private int padRight = 0;

    private Color borderColor;

    public MaCheckBox() {
        setDefaultStyle();
    }

    private void setDefaultStyle() {
        setFont(IBMPlexSansThaiFont.regular(14f));
        setForeground(Color.BLACK);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(padTop, padLeft, padBottom, padRight));
        updateSpace();
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

    private void updateSpace() {
        Border padding = BorderFactory.createEmptyBorder(padTop, padLeft, padBottom, padRight);
        setBorder(padding);
        repaint();
    }

    public void setPadding(int p) {
        this.padTop = p;
        this.padLeft = p;
        this.padBottom = p;
        this.padRight = p;
        updateSpace();
        repaint();
    }

    public void setPadding(int top, int left, int bottom, int right) {
        this.padTop = top;
        this.padLeft = left;
        this.padBottom = bottom;
        this.padRight = right;
        updateSpace();
        repaint();
    }

    //    https://www.comp.nus.edu.sg/~cs3283/ftp/Java/JavaTutorial/uiswing/painting/overview.html
    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());

        int y_padding = padBottom + padTop;
        if (arc != 0) {
            g2.fillRoundRect(0, 0, box_width, getHeight() - y_padding, arc, arc);
        } else {
            g2.fillRect(0, 0, box_width, getHeight() - y_padding);
        }

        g2.setColor(getForeground());
        g2.setFont(getFont());
//        System.out.println(getText() + getFont()+ getForeground());
//        print follolw text size because it print and text will be above of x,y and below so h = 0 not work
        FontMetrics fm = g2.getFontMetrics();
        int textY = fm.getAscent();
        g2.drawString(getText(), box_width + box_and_text_space, textY);
//        draw check
        if (isSelected()) {
            g2.setColor(Macolor.magreen);
//            g2.fillRect(0, 0, box_width / 2, getHeight() / 3);
            int r = Math.min(box_width,getHeight()- y_padding) / 5;

            g2.drawArc(box_width/2 -r  , (getHeight() - y_padding)/2 -r  ,r*2 ,r*2, 0,360);
            
            int r2 = Math.min(box_width,getHeight()- y_padding) / 8;

            g2.fillArc(box_width/2 -r2  , (getHeight() - y_padding)/2 -r2  ,r2*2 ,r2*2, 0,360);
        }
        //        use for test not draw check now
//        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderSize));
        int y_padding = padBottom + padTop;
        if (arc != 0) {
            g2.drawRoundRect(0, 0, box_width - borderSize, getHeight() - borderSize - y_padding, arc, arc);
        } else {
            g2.drawRect(0, 0, box_width - borderSize, getHeight() - borderSize - y_padding);
        }

        g2.dispose();
    }
}
