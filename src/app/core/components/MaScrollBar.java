/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.core.components;

/**
 *
 * @author poke
 */
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

// doc for make custom ScorllBar
// https://stackoverflow.com/questions/8208508/custom-design-jscollpane-java-swing
//https://stackoverflow.com/questions/44432037/basicscrollbarui-how-to-use-it
public class MaScrollBar extends BasicScrollBarUI {

    private final Dimension zeroDim = new Dimension();
    private int thickness = 6;

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(240, 240, 240, 0));
//        not work
//                g2.fillRect(trackBounds.x, trackBounds.y, thickness, trackBounds.height);
        g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        g2.dispose();
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Macolor.magreen);
        int arc = 10;
        g2.fillRoundRect(
                thumbBounds.x,
                thumbBounds.y,
                thickness,
                thumbBounds.height,
                arc, arc
        );

        g2.dispose();
    }

//    make track follow thickness 
    @Override
    public Dimension getPreferredSize(JComponent c) {
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            return new Dimension(thickness, super.getPreferredSize(c).height);
        } else {
            return new Dimension(super.getPreferredSize(c).width, thickness);
        }
    }

    // Remove arrow buttons by make w h =0
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return zeroDim;
            }
        };
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return zeroDim;
            }
        };
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

}
