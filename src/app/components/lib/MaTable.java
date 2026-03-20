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
import java.util.*;

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
        setArc(0);
        setPreferredSize(new Dimension(500, 250)); // helps NetBeans designer

        // Default data so designer can render the table
        model = new DefaultTableModel(
                new Object[][]{
                    {"-", "-", "-", "-"},
                    {"-", "-", "-", "-"},
                    {"-", "-", "-", "-"}, {"-", "-", "-", "-"},
                    {"-", "-", "-", "-"},
                    {"-", "-", "-", "-"},
                    {"-", "-", "-", "-"},
                    {"-", "-", "-", "-"},
                    {"-", "-", "-", "-"},
                    {"-", "-", "-", "-"},},
                new String[]{
                    "Col 1", "Col 2", "Col 3", "Col 4"
                }
        );

        table = new JTable(model);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFont(new Font("IBM Plex Sans Thai", Font.PLAIN, 14));

        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setSelectionBackground(new Color(210, 230, 255));
        table.setSelectionForeground(Color.BLACK);

        // Header styling
        JTableHeader header = table.getTableHeader();

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                label.setHorizontalAlignment(JLabel.LEFT);
                label.setBackground(Color.WHITE);
                label.setForeground(Macolor.magreen);
                label.setFont(new Font("IBM Plex Sans Thai", Font.BOLD, 14));
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                label.setOpaque(true);

                return label;
            }
        });
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(245, 245, 245));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }

                return c;
            }
        });

        scroll = new MaScrollPane(table);

        add(scroll, BorderLayout.CENTER);
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JTable getTable() {
        return table;
    }

    public void updateView(ArrayList<String> columns, ArrayList<Object[]> rows) {

        DefaultTableModel model = (DefaultTableModel) this.getModel();
        model.setColumnIdentifiers(columns.toArray());
        model.setRowCount(0);
        for (Object[] row : rows) {
            model.addRow(row);
        }
    }
}
