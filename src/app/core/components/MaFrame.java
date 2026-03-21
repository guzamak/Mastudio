/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.core.components;

import javax.swing.JFrame;

/**
 *
 * @author poke
 */
import javax.swing.*;
import java.awt.*;

public class MaFrame extends JFrame {

    public MaFrame(){
//        System.out.println("where my icon");
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src/resources/images/icon.jpg");
//        System.out.println(icon.getImage());
        setIconImage(icon.getImage());
        getContentPane().setBackground(Macolor.seablue);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }
}