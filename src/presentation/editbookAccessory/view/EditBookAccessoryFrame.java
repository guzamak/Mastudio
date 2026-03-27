/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation.editbookAccessory.view;

/**
 *
 * @author poke
 */

import app.core.components.*;
import app.core.components.fonts.IBMPlexSansThaiFont;
import presentation.booking.controller.Booking;
import presentation.roomAndaccessory.controller.Accessory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class EditBookAccessoryFrame extends MaInternalFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(EditBookAccessoryFrame.class.getName());

    private final Booking booking;
    private final BookAccessoryFrame parent;
    private final List<JCheckBox> checkBoxes = new ArrayList<>();

    public EditBookAccessoryFrame(Booking booking, BookAccessoryFrame parent) {
        this.booking = booking;
        this.parent = parent;
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        setTitle("แก้ไขอุปกรณ์การจอง");

        MaLabel infoLabel = new MaLabel();
        infoLabel.setText(
                "การจอง: " + booking.getRoom() + " | " + booking.getCustomer() + " | " + booking.getTimeSlot());
        infoLabel.setFont(IBMPlexSansThaiFont.medium(16f));
        infoLabel.setTextColor(Macolor.magreen);

        MaPanel checkPanel = new MaPanel();
        checkPanel.setPadding(20, 10, 0, 0);
        checkPanel.setBorderColor(Macolor.trans);
        checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.Y_AXIS));

        for (Accessory a : Accessory.data.values()) {

            MaCheckBox cb = new MaCheckBox();
            cb.setText(a.getName() + "  (" + (int) a.getPricePerHour() + " บาท/ชม)");
            cb.setOpaque(false);
            cb.setFont(IBMPlexSansThaiFont.light(13f));
            cb.putClientProperty("accessoryId", a.getId());
            cb.setFocusPainted(false);
            cb.setBorderColor(Macolor.magreen);
            cb.setArc(8);
            cb.setPadding(10, 10, 0, 0);
            cb.setSelected(booking.getAccessoryIds().contains(a.getId()));
            checkBoxes.add(cb);
            checkPanel.add(cb);
        }

        MaScrollPane scrollPane = new MaScrollPane(checkPanel);

        MaButton saveBtn = new MaButton();
        saveBtn.setText("บันทึก");
        saveBtn.setButtonColor(Macolor.magreen);
        saveBtn.setBorderColor(Macolor.trans);
        saveBtn.setArc(16);
        saveBtn.addActionListener(e -> save());

        MaPanel contentPanel = new MaPanel();
        contentPanel.setBorderColor(Macolor.trans);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        contentPanel.add(infoLabel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(saveBtn, BorderLayout.SOUTH);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(360, 420));
        pack();
    }

    private void save() {
        List<String> selectedIds = new ArrayList<>();
        for (JCheckBox cb : checkBoxes) {
            if (cb.isSelected()) {
                selectedIds.add((String) cb.getClientProperty("accessoryId"));
            }
        }
        booking.setAccessoryIds(selectedIds);
        Booking.putBooking(booking, logger);
        parent.loadData();
        dispose();
    }
}
