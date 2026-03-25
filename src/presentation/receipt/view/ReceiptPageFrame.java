/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentation.receipt.view;

/**
 *
 * @author poke
 */
import app.core.components.*;
import app.core.components.fonts.IBMPlexSansThaiFont;
import presentation.receipt.controller.Receipt;
import presentation.booking.controller.Booking;
import presentation.roomAndaccessory.controller.Accessory;
import presentation.roomAndaccessory.controller.Room;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;

public class ReceiptPageFrame extends MaInternalFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReceiptPageFrame.class.getName());

    private Receipt currentReceipt;
    private JLabel previewLabel;
    private MaButton saveBtn;
    private MaTable bookingTable;
    private ArrayList<Booking> bookingList = new ArrayList<>();

    public ReceiptPageFrame() {
        initComponents();
        setupReceiptUI();
    }

    public void setReceipt(Receipt receipt) {
        this.currentReceipt = receipt;
        showPreview();
    }

    public void setReceiptData(String customerName, String roomName, double roomPrice, String timeSlot, String date) {
        Receipt r = new Receipt();
        r.setCustomerName(customerName);
        if (date != null) r.setDate(date);
        r.addItem(roomName + " (" + timeSlot + ")", roomPrice);
        setReceipt(r);
    }

    private void setupReceiptUI() {
        Accessory.loadAccessories(logger);

        // --- Booking table (center panel) ---
        bookingTable = new MaTable();
        loadBookingTable();

        MaLabel bookingLabel = new MaLabel();
        bookingLabel.setText("เลือก Booking ที่ต้องการออกใบเสร็จ");
        bookingLabel.setFont(IBMPlexSansThaiFont.medium(14f));

        MaButton generateBtn = new MaButton();
        generateBtn.setText("ออกใบเสร็จ");
        generateBtn.setArc(16);
        generateBtn.setButtonColor(Macolor.magreen);
        generateBtn.addActionListener(e -> generateReceipt());

        MaButton refreshBtn = new MaButton();
        refreshBtn.setText("Refresh");
        refreshBtn.setArc(16);
        refreshBtn.setButtonColor(Macolor.mablue);
        refreshBtn.addActionListener(e -> loadBookingTable());

        JPanel tableBtnPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        tableBtnPanel.setOpaque(false);
        tableBtnPanel.add(refreshBtn);
        tableBtnPanel.add(generateBtn);

        JScrollPane tableScroll = new JScrollPane(bookingTable);
        tableScroll.setBorder(BorderFactory.createEmptyBorder());
        tableScroll.getViewport().setBackground(Color.WHITE);

        MaPanel centerPanel = new MaPanel();
        centerPanel.setBorderColor(Macolor.magreen);
        centerPanel.setLayout(new BorderLayout(0, 10));
        centerPanel.add(bookingLabel, BorderLayout.NORTH);
        centerPanel.add(tableScroll, BorderLayout.CENTER);
        centerPanel.add(tableBtnPanel, BorderLayout.SOUTH);

        // --- Receipt preview panel (right) ---
        previewLabel = new JLabel();
        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane previewScroll = new JScrollPane(previewLabel);
        previewScroll.setBorder(BorderFactory.createEmptyBorder());
        previewScroll.getViewport().setBackground(new Color(235, 240, 245));

        saveBtn = new MaButton();
        saveBtn.setText("บันทึกเป็นรูปภาพ (Save PNG)");
        saveBtn.setArc(16);
        saveBtn.setButtonColor(Macolor.magreen);
        saveBtn.addActionListener(e -> saveReceiptImage());

        MaPanel rightPanel = new MaPanel();
        rightPanel.setBorderColor(Macolor.trans);
        rightPanel.setLayout(new BorderLayout(0, 12));
        rightPanel.add(previewScroll, BorderLayout.CENTER);
        rightPanel.add(saveBtn, BorderLayout.SOUTH);

        // Add new components to content pane
        getContentPane().add(centerPanel);
        getContentPane().add(rightPanel);

        GroupLayout layout = (GroupLayout) getContentPane().getLayout();
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(18)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(navLabel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(maPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(18)
                            .addComponent(centerPanel, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE)
                            .addGap(18)
                            .addComponent(rightPanel, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(navLabel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(18)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(maPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(centerPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rightPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(18, Short.MAX_VALUE))
        );
        pack();
    }

    private void loadBookingTable() {
        bookingList.clear();

        ArrayList<String> columns = new ArrayList<>();
        columns.add("ห้อง");
        columns.add("ลูกค้า");
        columns.add("วันที่");
        columns.add("เวลา");
        columns.add("อุปกรณ์");

        ArrayList<Object[]> rows = new ArrayList<>();
        for (Booking b : Booking.data.values()) {
            bookingList.add(b);

            // Build accessory names string
            StringBuilder accNames = new StringBuilder();
            for (String accId : b.getAccessoryIds()) {
                Accessory a = Accessory.data.get(accId);
                if (a != null) {
                    if (accNames.length() > 0) accNames.append(", ");
                    accNames.append(a.getName());
                }
            }

            rows.add(new Object[]{
                b.getRoom(),
                b.getCustomer(),
                b.getCheckIn() != null ? b.getCheckIn().substring(0, 10) : "",
                b.getTimeSlot(),
                accNames.length() > 0 ? accNames.toString() : "-"
            });
        }
        bookingTable.updateView(columns, rows);
    }

    private void generateReceipt() {
        int row = bookingTable.getSelectedRow();
        if (row < 0 || row >= bookingList.size()) {
            MaOptionPane.showMessageDialog(this, "กรุณาเลือก Booking ในตารางก่อน");
            return;
        }

        Booking b = bookingList.get(row);
        double hours = parseHours(b.getTimeSlot());

        Room room = Room.data.get(b.getRoomId());
        String roomName = room != null ? room.getName() : b.getRoom();
        double roomPrice = room != null ? room.getPricePerHour() : 0.0;

        Receipt r = new Receipt();
        r.setCustomerName(b.getCustomer());
        r.addItem(roomName + " (" + b.getTimeSlot() + ")", roomPrice * hours);

        for (String accId : b.getAccessoryIds()) {
            Accessory a = Accessory.data.get(accId);
            if (a != null) {
                r.addItem(a.getName() + " (" + b.getTimeSlot() + ")", a.getPricePerHour() * hours);
            }
        }

        setReceipt(r);
    }

    private double parseHours(String timeSlot) {
        try {
            String[] parts = timeSlot.split("-");
            double start = Double.parseDouble(parts[0]);
            double end = Double.parseDouble(parts[1]);
            return end - start;
        } catch (Exception e) {
            return 1.0;
        }
    }

    private void showPreview() {
        if (currentReceipt == null) return;
        BufferedImage img = currentReceipt.toImage();
        previewLabel.setIcon(new ImageIcon(img));
        previewLabel.revalidate();
    }

    private void saveReceiptImage() {
        if (currentReceipt == null) {
            MaOptionPane.showMessageDialog(this, "ยังไม่มีใบเสร็จให้บันทึก\nกรุณาเลือก Booking แล้วกด 'ออกใบเสร็จ' ก่อน");
            return;
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("เลือกโฟลเดอร์สำหรับบันทึกใบเสร็จ");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File saved = currentReceipt.saveAsImage(chooser.getSelectedFile().getAbsolutePath());
                MaOptionPane.showMessageDialog(this, "บันทึกสำเร็จ!\n" + saved.getAbsolutePath());
            } catch (java.io.IOException ex) {
                logger.log(java.util.logging.Level.SEVERE, "Failed to save receipt", ex);
                MaOptionPane.showMessageDialog(this, "เกิดข้อผิดพลาดในการบันทึก");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        navLabel1 = new app.core.components.NavLabel();
        navLabel1.setExtraText("ออกใบเสร็จ");
        maPanel1 = new app.core.components.MaPanel();
        maScrollPane1 = new app.core.components.MaScrollPane();
        maTextArea1 = new app.core.components.MaTextArea();
        maLabel1 = new app.core.components.MaLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        maPanel1.setBackground(Color.white);
        maPanel1.setBorderColor(Macolor.magreen);

        maScrollPane1.setScrollX(false);
        maScrollPane1.scrollToTop();
        maScrollPane1.getViewport().setBackground(Color.white);

        maTextArea1.setTextColor(Macolor.magreen);
        maTextArea1.setFont(IBMPlexSansThaiFont.light(12f));
        maTextArea1.setEditable(false);
        maTextArea1.setBackground(Color.white);
        maTextArea1.setColumns(20);
        maTextArea1.setRows(5);
        maTextArea1.setText("วิธีการใช้งาน\n\n1. เลือก Booking จากตารางกลาง\n2. กดปุ่ม 'ออกใบเสร็จ'\n3. ใบเสร็จจะแสดงพร้อมราคา\n   ห้อง + อุปกรณ์ × จำนวนชั่วโมง\n4. กด 'บันทึกเป็นรูปภาพ'\n   เพื่อ save PNG\n\n* กด Refresh เพื่อโหลด\n  ข้อมูลการจองล่าสุด");
        maScrollPane1.setViewportView(maTextArea1);

        javax.swing.GroupLayout maPanel1Layout = new javax.swing.GroupLayout(maPanel1);
        maPanel1.setLayout(maPanel1Layout);
        maPanel1Layout.setHorizontalGroup(
            maPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
            .addGroup(maPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(maPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(maScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        maPanel1Layout.setVerticalGroup(
            maPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 325, Short.MAX_VALUE)
            .addGroup(maPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(maPanel1Layout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addComponent(maScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                    .addGap(10, 10, 10)))
        );

        maLabel1.setText("รายชื่อห้อง (ต้องการออกใบเสร็จห้องไหน )");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(maPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(maLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(navLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(231, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(navLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(maPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(101, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new ReceiptPageFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.core.components.MaLabel maLabel1;
    private app.core.components.MaPanel maPanel1;
    private app.core.components.MaScrollPane maScrollPane1;
    private app.core.components.MaTextArea maTextArea1;
    private app.core.components.NavLabel navLabel1;
    // End of variables declaration//GEN-END:variables
}
