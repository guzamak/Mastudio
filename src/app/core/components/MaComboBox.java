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
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;

public class MaComboBox<E> extends MaPanel implements ActionListener, MouseListener {

    private MaLabel display;
    private MaButton arrow;

    private JPopupMenu popup;
    private JWindow popupWindow;
    private MaList<E> list;
    private MaScrollPane scroll;

    private int arc = 20;

    public MaComboBox() {

        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(200, 35));

        display = new MaLabel();
        display.setText("");
        display.setBorder(new EmptyBorder(5, 10, 5, 10));

        arrow = new MaButton();
        arrow.setText("  v  ");
        arrow.setArc(arc);
        arrow.setBackground(Macolor.magreen);
        arrow.setFocusable(false);
        arrow.setBorder(null);
        arrow.setContentAreaFilled(false);

        add(display, BorderLayout.CENTER);
        add(arrow, BorderLayout.EAST);

        arrow.addActionListener(this);
        display.addMouseListener(this);

        list = new MaList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addMouseListener(this);
        MaPanel panel = new MaPanel();
        panel.setBackground(Macolor.trans);
        panel.setBorderColor(Macolor.trans);

//        popup.setBorder(null); now work need this to work i dkw
//        Border redLine = BorderFactory.createLineBorder(Color.RED, 0); 
//        Border emptyPadding = BorderFactory.createEmptyBorder(1, 1, 1, 1); 
//        Border compoundBorder = BorderFactory.createCompoundBorder(redLine, emptyPadding); 
//        popup = new JPopupMenu();
//        popup.setLightWeightPopupEnabled(true);
//        if popup append outside of frame it will create bg anyway but jwindow not
//        popup.setBackground(Macolor.trans);
//        popup.setBorder(compoundBorder);
//      I cant fix bg and border of it 
        scroll = new MaScrollPane(list);
        panel.add(scroll, BorderLayout.CENTER);
//        not work
//        scroll.setPreferredSize(new Dimension(display.getwidth(), 120));
//        scroll.getViewport().setOpaque(false);
//        scroll.getViewport().setBackground(Macolor.trans);
//        scroll.getViewport().setBorder(null);

        popupWindow = new JWindow();
        popupWindow.setBackground(new Color(0, 0, 0, 0)); // true transparency
        popupWindow.add(panel);
        popupWindow.pack();
        popupWindow.setVisible(false);
//        popup.add(panel);
    }

//    private void togglePopup() {
//
//        if (popup.isVisible()) {
//            popup.setVisible(false);
//        } else {
//            popup.show(this, 0, getHeight());
//        }
//
//    }
    private void togglePopup() {

        if (popupWindow.isVisible()) {
            popupWindow.setVisible(false);
            return;
        }

//        get checkbox location + checkbox hieght to find y and x = same as checkbox  
        Point p = getLocationOnScreen();

        int popupHeight = popupWindow.getHeight();
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = getWidth();
        int height = list.getHeight();

        scroll.setPreferredSize(new Dimension(width, height));
        popupWindow.pack();

        int y;
//        if y + pophp height out of screen make it another wway
        if (p.y + getHeight() + popupHeight > screenHeight) {
            y = p.y - popupHeight;
        } else {
            y = p.y + getHeight();
        }

        popupWindow.setLocation(p.x, y);
        popupWindow.setVisible(true);
    }

    public void setList(E[] items) {
        list.setListData(items);
    }

    public E getSelectedItem() {
        return list.getSelectedValue();
    }

    public void setArc(int arc) {
        this.arc = arc;
        repaint();
    }

    public MaButton getArrow() {
        return arrow;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();

        if (src == arrow) {
            togglePopup();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Object src = e.getSource();

        if (src == display) {
            togglePopup();
        } else if (src == list) {

            E value = list.getSelectedValue();

            if (value != null) {
                display.setText(value.toString());
            }

            popupWindow.setVisible(false);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        g2.setColor(new Color(180, 180, 180));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

        g2.dispose();
    }
}
