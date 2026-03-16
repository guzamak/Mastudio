/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.component;

/**
 *
 * @author poke
 */
import core.component.font.IBMPlexSansThaiFont;
import javax.swing.*;
import java.awt.*;

public class MaButton extends JButton {

    private int arc = 20;
    private int borderSize = 1;
    private boolean round = true;
    private Color borderColor = new Color(41,128,185);

    public MaButton() {
        setDefaultStyle();
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void setDefaultStyle(){
        setFont(IBMPlexSansThaiFont.regular(14f));
        setForeground(Color.WHITE);
        setBackground(new Color(52,152,219));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    }

    public void setArc(int arc){
        this.arc = arc;
        repaint();
    }

    public void setBorderSize(int size){
        this.borderSize = size;
        repaint();
    }

    public void setBorderColor(Color color){
        this.borderColor = color;
        repaint();
    }

    public void setRound(boolean round){
        this.round = round;
        repaint();
    }

    public void setFontSize(int size){
        setFont(getFont().deriveFont((float)size));
    }

    public void setFontName(String name){
        setFont(new Font(name, Font.BOLD, getFont().getSize()));
    }

    public void setTextColor(Color color){
        setForeground(color);
    }

    public void setButtonColor(Color color){
        setBackground(color);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(getBackground());

        if(round){
            g2.fillRoundRect(0,0,getWidth(),getHeight(),arc,arc);
        }else{
            g2.fillRect(0,0,getWidth(),getHeight());
        }

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {

        if(borderSize > 0){

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderSize));

            if(round){
                g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,arc,arc);
            }else{
                g2.drawRect(0,0,getWidth()-1,getHeight()-1);
            }

            g2.dispose();
        }
    }
}
