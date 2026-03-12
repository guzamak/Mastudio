/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.ui;

/**
 *
 * @author poke
 */
import app.components.dashboard.NavLabel;
import app.components.lib.MaComboBox;
import app.components.lib.MaFrame;
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
        cb.setList(animals);
        add(cb);
        add(navlabel);  
//        add(panel, BorderLayout.CENTER);
    }
//    use for test code
    public static void main(String[] args) {
        new DashboardFrameExample().setVisible(true);
    }

}