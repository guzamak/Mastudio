package presentation.editbookAccessory.view;

import app.core.components.*;
import app.core.components.fonts.IBMPlexSansThaiFont;
import presentation.booking.controller.Booking;
import presentation.roomAndaccessory.controller.Accessory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class EditBookAccessoryFrame extends MaInternalFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EditBookAccessoryFrame.class.getName());

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

        // --- Info panel (top) ---
        MaLabel infoLabel = new MaLabel();
        infoLabel.setText("การจอง: " + booking.getRoom() + " | " + booking.getCustomer() + " | " + booking.getTimeSlot());
        infoLabel.setFont(IBMPlexSansThaiFont.medium(14f));

        // --- Accessories checkbox panel ---
        JPanel checkPanel = new JPanel();
        checkPanel.setBackground(Color.WHITE);
        checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.Y_AXIS));

        for (Accessory a : Accessory.data.values()) {
            JCheckBox cb = new JCheckBox(a.getName() + "  (" + (int) a.getPricePerHour() + " บาท/hr)");
            cb.setBackground(Color.WHITE);
            cb.setFont(IBMPlexSansThaiFont.regular(13f));
            cb.putClientProperty("accessoryId", a.getId());
            cb.setSelected(booking.getAccessoryIds().contains(a.getId()));
            checkBoxes.add(cb);
            checkPanel.add(cb);
        }

        JScrollPane scrollPane = new JScrollPane(checkPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(Macolor.magreen, 1, true));
        scrollPane.getViewport().setBackground(Color.WHITE);

        // --- Save button ---
        MaButton saveBtn = new MaButton();
        saveBtn.setText("บันทึก");
        saveBtn.setButtonColor(Macolor.magreen);
        saveBtn.setBorderColor(Macolor.trans);
        saveBtn.setArc(16);
        saveBtn.addActionListener(e -> save());

        // --- Layout ---
        MaPanel contentPanel = new MaPanel();
        contentPanel.setBorderColor(Macolor.magreen);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BorderLayout(0, 12));
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
