/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.components.lib;

/**
 *
 * @author poke
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;


public class MaComboBox<E> extends MaPanel implements ActionListener, MouseListener {

    private MaLabel display;
    private MaButton arrow;

    private JPopupMenu popup;
    private MaList<E> list;

    private int arc = 20;

    public MaComboBox() {

        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(200, 35));

        display = new MaLabel();
        display.setText("Select...");
        display.setBorder(new EmptyBorder(5,10,5,10));

        arrow = new MaButton();
        arrow.setText("  v  ");
        arrow.setArc(arc);
        arrow.setBackground(Macolor.magreen);
        arrow.setFocusable(false);
        arrow.setBorder(null);
        arrow.setContentAreaFilled(false);

        add(display, BorderLayout.CENTER);
        add(arrow, BorderLayout.EAST);

        // register listeners
        arrow.addActionListener(this);
        display.addMouseListener(this);

        list = new MaList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addMouseListener(this);

        popup = new JPopupMenu();

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);
        scroll.setPreferredSize(new Dimension(200,120));

        popup.add(scroll);
    }

    private void togglePopup() {

        if(popup.isVisible()){
            popup.setVisible(false);
        } else {
            popup.show(this,0,getHeight());
        }

    }

    public void setList(E[] items){
        list.setListData(items);
    }

    public E getSelectedItem(){
        return list.getSelectedValue();
    }

    public void setArc(int arc){
        this.arc = arc;
        repaint();
    }

    // ========================
    // ActionListener
    // ========================

    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();

        if(src == arrow){
            togglePopup();
        }

    }

    // ========================
    // MouseListener
    // ========================

    @Override
    public void mouseClicked(MouseEvent e) {

        Object src = e.getSource();

        if(src == display){
            togglePopup();
        }

        else if(src == list){

            E value = list.getSelectedValue();

            if(value != null){
                display.setText(value.toString());
            }

            popup.setVisible(false);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    // ========================
    // Paint
    // ========================

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0,0,getWidth(),getHeight(),arc,arc);

        g2.setColor(new Color(180,180,180));
        g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,arc,arc);

        g2.dispose();
    }
}