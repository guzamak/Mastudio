/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.core.components;

import java.awt.*;
import javax.swing.*;

public class MaFrame extends JFrame {

    public MaFrame(){
//        System.out.println("where my icon");
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src/resources/images/icon.jpg");
//        System.out.println(icon.getImage());
        setIconImage(icon.getImage());
        getContentPane().setBackground(new Color(240,248,255));
        setResizable(false);
    }
}