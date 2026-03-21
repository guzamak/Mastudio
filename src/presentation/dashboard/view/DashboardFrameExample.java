/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation.dashboard.view;

/**
 *
 * @author poke
 */
import app.core.components.NavLabel;
import app.core.components.MaComboBox;
import app.core.components.MaFrame;
import app.core.components.MaList;
import app.core.components.MaTable;
import javax.swing.*;
import java.awt.*;

public class DashboardFrameExample extends MaFrame {

    public DashboardFrameExample(){
        setLayout(new FlowLayout(FlowLayout.LEFT));
        NavLabel navlabel = new NavLabel();
        navlabel.setExtraText("DashBoard.");
        setTitle("Dashboard");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Welcome to Dashboard", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        panel.add(title, BorderLayout.CENTER);
        String[] animals = {"dog", "Bird","dog","dog","dog","dog","dog","dog","dog","dog","dog","dog","dog","dog","dog","dog","dog","dog",};
        MaComboBox cb = new MaComboBox();
        MaList l = new MaList();
        l.setListData(animals);
        add(l);
        
        cb.setList(animals);
        add(cb);
        add(navlabel);  
//        add(panel, BorderLayout.CENTER);
        MaTable table = new MaTable();
        table.getModel().setColumnIdentifiers(new Object[]{
            "ID", "Name", "Age"
        });

        table.getModel().addRow(new Object[]{1,"John",20});
        table.getModel().addRow(new Object[]{2,"Anna",22});
        table.getModel().addRow(new Object[]{3,"Mike",25});

        add(table, BorderLayout.CENTER);
    }
//    use for test code
    public static void main(String[] args) {
        new DashboardFrameExample().setVisible(true);
    }

}