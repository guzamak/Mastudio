/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.core.components;

import app.core.components.fonts.IBMPlexSansThaiFont;
import javax.swing.JInternalFrame;

/**
 *
 * @author poke
 */
import javax.swing.*;
import java.awt.*;
// important for custom with override paintcomponents
import javax.swing.plaf.basic.*;
import javax.swing.plaf.metal.MetalInternalFrameTitlePane;
import javax.swing.plaf.metal.MetalInternalFrameUI;

// doc
// custom JinternalFrame with uiMa
//http://coderanch.com/t/338849/java/customize-title-bar-JInternalFrame
// custom with override paintcomponents (use this one)
//https://forums.oracle.com/ords/apexds/post/jinternalframe-titlebar-color-4414

public class MaInternalFrame extends JInternalFrame {

    public MaInternalFrame() {
        super("Internal Frame", false, true, false, false);
        setClosable(true);
//        icon not work like frame
//        ImageIcon icon = new ImageIcon("src/resources/images/icon.jpg");
//        setFrameIcon(icon);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBorder(BorderFactory.createEmptyBorder());
        ColorTitlebarInternalFrameUI ctifUI = new ColorTitlebarInternalFrameUI(this);
        setUI(ctifUI);
        setBackground(Macolor.seablue);
        setSize(300, 200);

        setLocation(50, 50);
        setVisible(true);
    }

    class ColorTitlebarInternalFrameUI extends MetalInternalFrameUI {

        public ColorTitlebarInternalFrameUI(JInternalFrame jif) {

            super(jif);
        }

        protected JComponent createNorthPane(JInternalFrame jif) {

            MetalInternalFrameTitlePane titlePane = new MetalInternalFrameTitlePane(jif) {
                @Override
                public void paintComponent(Graphics g) {
//                    not work because it incule bg can ctal click paintComponent to view defualt paintComponent
                    super.paintComponent(g);

                    Graphics2D g2 = (Graphics2D) g;
                    g2.setBackground(new Color(244,244,244));
                    g2.clearRect(0, 0, getWidth(), getHeight());
                    g.setColor(Macolor.magreen);
                    g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
//       copy form https://forums.oracle.com/ords/apexds/post/jinternalframe-titlebar-color-4414
                    int titleLength = 0;
//                    xoffset == x point in navbar left =0 rigth = max_w
                    int xOffset = 5;
                    String frameTitle = frame.getTitle();

                    ImageIcon originalIcon = new ImageIcon("src/resources/images/icon.jpg");
                    Image scaledImage = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);

                    ImageIcon icon = new ImageIcon(scaledImage);
//                    default jinternal icon
//                    Icon icon = frame.getFrameIcon();
                    if (icon != null) {
                        int iconY = ((getHeight() / 2) - (icon.getIconHeight() / 2));
                        icon.paintIcon(frame, g, xOffset, iconY);
                        xOffset += icon.getIconWidth() + 5;
                    }

//                    idk what is this do just copy paste form oracle 
                    if (frameTitle != null) {
                        g.setColor(Color.black);
                        g.setFont(IBMPlexSansThaiFont.regular(14f));
                        FontMetrics fm = getFontMetrics(getFont());
                        int fHeight = fm.getHeight();
                        int yOffset = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();

                        Rectangle rect = new Rectangle(0, 0, 0, 0);
                        if (frame.isIconifiable()) {
                            rect = iconButton.getBounds();
                        } else if (frame.isMaximizable()) {
                            rect = maxButton.getBounds();
                        } else if (frame.isClosable()) {
                            rect = closeButton.getBounds();
                        }
                        int titleW;

                        if (rect.x == 0) {
                            rect.x = frame.getWidth() - frame.getInsets().right - 2;
                        }
                        titleW = rect.x - xOffset - 4;
                        frameTitle = getTitle(frameTitle, fm, titleW);

                        titleLength = fm.stringWidth(frameTitle);
                        g2.drawString(frameTitle, xOffset, yOffset);
                        xOffset += titleLength + 5;
                    }
                }
//                @Override
//                public void paintBorder(Graphics g) {
////                    not paint border
//                }
            };
            return titlePane;
        }
    }
}
