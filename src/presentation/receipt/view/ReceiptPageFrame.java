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
import javax.swing.border.Border;
import presentation.payment.controller.Payment;
import static presentation.payment.controller.Payment.alreadyCheckBookId;

public class ReceiptPageFrame extends MaInternalFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReceiptPageFrame.class.getName());

    private Receipt currentReceipt;
    private Booking selectedBooking;
    private double selectedTotal;
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
        Payment.loadPayments(logger);
        System.out.println(Payment.alreadyCheckBookId.size());
        Accessory.loadAccessories(logger);
        loadBookingTable();

        MaLabel bookingLabel = new MaLabel();
        bookingLabel.setText("เลือก Booking ที่ต้องการออกใบเสร็จ");
        bookingLabel.setFont(IBMPlexSansThaiFont.medium(14f));
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

        double total = roomPrice * hours;
        for (String accId : b.getAccessoryIds()) {
            Accessory a = Accessory.data.get(accId);
            if (a != null) {
                r.addItem(a.getName() + " (" + b.getTimeSlot() + ")", a.getPricePerHour() * hours);
                total += a.getPricePerHour() * hours;
            }
        }

        selectedBooking = b;
        selectedTotal = total;
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
            MaOptionPane.showMessageDialog(this, "โปรดกดออกใบเสร็จก่อนบันทึก");
            return;
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("เลือกโฟลเดอร์สำหรับบันทึกใบเสร็จ");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File saved = currentReceipt.saveAsImage(chooser.getSelectedFile().getAbsolutePath());
                MaOptionPane.showMessageDialog(this, "บันทึกที่" + saved.getAbsolutePath());
            } catch (java.io.IOException ex) {
                logger.log(java.util.logging.Level.SEVERE, "Failed to save receipt", ex);
                MaOptionPane.showMessageDialog(this, "เกิดข้อผิดผลาดระหว่างบันทึก");
            }
        }
    }
    
     private void recordPayment() {
        if (selectedBooking == null || currentReceipt == null) {
            MaOptionPane.showMessageDialog(this, "โปรดเลือกการจอง");
            return;
        }
        if (Payment.alreadyCheckBookId.contains(selectedBooking.getId())){
            MaOptionPane.showMessageDialog(this, "การจองนี้ได้ชำระเงินเเล้ว");
            return;
        }
        Payment p = Payment.postPayment(selectedBooking.getId(), selectedTotal, logger);
        if (p == null) {
            MaOptionPane.showMessageDialog(this, "เกิดข้อผิดผลาด");
        } else {
            MaOptionPane.showMessageDialog(this, "บันทึกลงในประวิติการจ่ายเงิน");
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

        jCheckBox1 = new javax.swing.JCheckBox();
        navLabel1 = new app.core.components.NavLabel();
        navLabel1.setExtraText("ออกใบเสร็จ");
        maLabel1 = new app.core.components.MaLabel();
        generateBtn = new app.core.components.MaButton();
        maScrollPane1 = new app.core.components.MaScrollPane();
        previewLabel = new app.core.components.MaLabel();
        saveBtn = new app.core.components.MaButton();
        bookingTable = new app.core.components.MaTable();
        maLabel2 = new app.core.components.MaLabel();
        paymentBtn = new app.core.components.MaButton();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ออกใบเสร็จ");

        maLabel1.setText("เลือกห้องที่ต้องการออกใบเสร็จ");
        maLabel1.setTextColor(Macolor.magreen);
        maLabel1.setFont(IBMPlexSansThaiFont.medium(16));

        generateBtn.setText("+ เลือกการจอง");
        generateBtn.setBorderColor(Macolor.trans);
        generateBtn.setTextColor(Macolor.trans);
        generateBtn.setButtonColor(Macolor.trans);
        generateBtn.setTextColor(Macolor.magreen);
        generateBtn.addActionListener(this::generateBtnActionPerformed);

        Border greenline = BorderFactory.createLineBorder(Macolor.mablue, 1);
        maScrollPane1.setBorder(greenline);
        maScrollPane1.setBackground(new Color(245,245,245));

        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        maScrollPane1.setViewportView(previewLabel);

        saveBtn.setText("บันทึกใบเสร็จ ( .png )");
        saveBtn.setButtonColor(Color.white);
        saveBtn.setBorderColor(Macolor.magreen);
        saveBtn.setTextColor(Macolor.magreen);
        saveBtn.setArc(35);
        saveBtn.addActionListener(this::saveBtnActionPerformed);

        maLabel2.setText("ใบเสร็จ ( preview )");
        maLabel2.setTextColor(Macolor.magreen);
        maLabel2.setFont(IBMPlexSansThaiFont.medium(16));

        paymentBtn.setText("ชำระเงินเเล้ว");
        paymentBtn.setBorderColor(Macolor.trans);
        paymentBtn.setTextColor(Macolor.trans);
        paymentBtn.setButtonColor(Macolor.trans);
        paymentBtn.setTextColor(Macolor.mared);
        paymentBtn.addActionListener(this::paymentBtnActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(maLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(generateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bookingTable, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(maLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(paymentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(maScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 832, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(navLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(navLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(maLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(generateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(paymentBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(maLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(maScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bookingTable, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateBtnActionPerformed
        // TODO add your handling code here:
        generateReceipt();
    }//GEN-LAST:event_generateBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        // TODO add your handling code here:
        saveReceiptImage();
    }//GEN-LAST:event_saveBtnActionPerformed

    private void paymentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentBtnActionPerformed
        // TODO add your handling code here:
        recordPayment();
    }//GEN-LAST:event_paymentBtnActionPerformed

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
    private app.core.components.MaTable bookingTable;
    private app.core.components.MaButton generateBtn;
    private javax.swing.JCheckBox jCheckBox1;
    private app.core.components.MaLabel maLabel1;
    private app.core.components.MaLabel maLabel2;
    private app.core.components.MaScrollPane maScrollPane1;
    private app.core.components.NavLabel navLabel1;
    private app.core.components.MaButton paymentBtn;
    private app.core.components.MaLabel previewLabel;
    private app.core.components.MaButton saveBtn;
    // End of variables declaration//GEN-END:variables
}
