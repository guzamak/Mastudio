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
import java.awt.*;

public class MaScrollPane extends JScrollPane {

    private boolean scrollX = true;
    private boolean scrollY = true;
    private MaScrollBar hscrollbar;
    private MaScrollBar vscrollbar;

    public MaScrollPane(Component view) {
        super(view);
        init();
    }

    public MaScrollPane() {
        super();
        init();
    }

    private void init() {

        setBorder(null);

        getViewport().setBackground(Macolor.seablue);

        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        vscrollbar = new MaScrollBar();
        hscrollbar = new MaScrollBar();

        getVerticalScrollBar().setUI(vscrollbar);
        getHorizontalScrollBar().setUI(hscrollbar);

    }

    private void updateScrollSetting() {
        setVerticalScrollBarPolicy(
                scrollY ? JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                        : JScrollPane.VERTICAL_SCROLLBAR_NEVER
        );

        setHorizontalScrollBarPolicy(
                scrollX ? JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
                        : JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
    }

    public void setScrollY(boolean scrollY) {
        this.scrollY = scrollY;
        updateScrollSetting();
    }

    public void setScrollX(boolean scrollX) {
        this.scrollX = scrollX;
        updateScrollSetting();
    }

    public void setScrollBarThickness(int size) {
        if (vscrollbar != null) {
            vscrollbar.setThickness(size);
        }
        if (hscrollbar != null) {
            hscrollbar.setThickness(size);
        }
    }
    

public void scrollToTop() {
    SwingUtilities.invokeLater(() -> {
        getViewport().setViewPosition(new Point(0, 0));
    });
}

}
