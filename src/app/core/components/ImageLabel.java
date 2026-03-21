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
import java.awt.*;

public class ImageLabel extends JLabel {

    private Image image;

    public ImageLabel(){
        setSize(200,200);
//        label3.setOpaque(true);
//        label3.setBackground(Color.blue);
    }
    public void setImage(String path) {
        image = new ImageIcon(path).getImage();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
//        not paint text
//        super.paintComponent(g);

        if (image != null) {
            int labelWidth = getWidth();
            int labelHeight = getHeight();

            int imgWidth = image.getWidth(this);
            int imgHeight = image.getHeight(this);

            double widthRatio = (double) labelWidth / imgWidth;
            double heightRatio = (double) labelHeight / imgHeight;
            double ratio = Math.min(widthRatio, heightRatio);

            int newWidth = (int) (imgWidth * ratio);
            int newHeight = (int) (imgHeight * ratio);

            int x = (labelWidth - newWidth) / 2;
            int y = (labelHeight - newHeight) / 2;

            g.drawImage(image, x, y, newWidth, newHeight, this);
        }
    }
}
