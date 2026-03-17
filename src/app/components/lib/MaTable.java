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
import javax.swing.table.*;
import java.awt.*;

// cant make round border i dont know how to make 
//doc
//https://www.youtube.com/watch?v=ABTaFh2Sj9w
//https://www.youtube.com/watch?v=J6ybK_K00G4
//https://www.youtube.com/watch?v=yJUQshXN_EY


public class MaTable extends MaPanel {

    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scroll;

    private int arc = 20;

    public MaTable() {

        setLayout(new BorderLayout());
        setOpaque(false);
        setArc(0);

        model = new DefaultTableModel();

        table = new JTable(model);
        table.setOpaque(false);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));
        table.setFont(new Font("IBM Plex Sans Thai", Font.PLAIN, 14));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("IBM Plex Sans Thai", Font.BOLD, 14));
        header.setOpaque(false);
        header.setForeground(Macolor.magreen);
        header.setBackground(Macolor.bggrey);
        header.setReorderingAllowed(false);

        scroll = new JScrollPane(table);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        add(scroll, BorderLayout.CENTER);
    }

    public DefaultTableModel getModel(){
        return model;
    }

    public JTable getTable(){
        return table;
    }

}