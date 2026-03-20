/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.components.lib;

/**
 *
 * @author poke
 */
import java.awt.*;
import javax.swing.*;

public class RefreshButton extends MaButton {

    public RefreshButton() {
        super();
        setText(null); // no text
        setPreferredSize(new Dimension(50, 50));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawRefreshIcon(g2);

        g2.dispose();
    }

    private void drawRefreshIcon(Graphics2D g2) {
        int size = Math.min(getWidth(), getHeight()) - 16;

        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(getForeground());

//      draw circle
        g2.drawArc(x, y, size, size, 40, 280);

        // Arrow head position
        double angle = Math.toRadians(325); 

        int cx = x + size / 2;
        int cy = y + size / 2;
        int r = size / 2;

//        need to draw triangle to understand
//        center of cicle(change defult positon (0,0) form topleft to center of screen) 
// + (r * Math.cos(angle) (x position of angle ) + storke size) 
        int ax = (int) (cx + r * Math.cos(angle) + 2.5f);
        int ay = (int) (cy - r * Math.sin(angle) + 2.5f); 

//        draw triagle
        Polygon arrow = new Polygon();
        arrow.addPoint(ax, ay);
        arrow.addPoint(ax - 8, ay - 4);
        arrow.addPoint(ax - 4, ay + 8);

        g2.fill(arrow);
    }
}
