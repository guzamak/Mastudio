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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;

public class ReceiptPageFrame extends MaInternalFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReceiptPageFrame.class.getName());

    private Receipt currentReceipt;
    private JLabel previewLabel;
    private MaButton saveBtn;

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
        previewLabel = new JLabel();
        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane previewScroll = new JScrollPane(previewLabel);
        previewScroll.setBorder(BorderFactory.createEmptyBorder());
        previewScroll.getViewport().setBackground(new Color(235, 240, 245));
        previewScroll.setPreferredSize(new Dimension(440, 500));

        saveBtn = new MaButton();
        saveBtn.setText("บันทึกเป็นรูปภาพ (Save PNG)");
        saveBtn.setArc(16);
        saveBtn.setButtonColor(Macolor.magreen);
        saveBtn.addActionListener(e -> saveReceiptImage());

        MaButton testBtn = new MaButton();
        testBtn.setText("ทดสอบใบเสร็จ (Test)");
        testBtn.setArc(16);
        testBtn.setButtonColor(Macolor.mablue);
        testBtn.addActionListener(e -> {
            Receipt r = new Receipt();
            r.setCustomerName("ทดสอบ Test Customer");
            r.addItem("Room A (10.00-11.00)", 500.0);
            r.addItem("Microphone", 200.0);
            r.addItem("Speaker", 150.0);
            setReceipt(r);
        });

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(testBtn);
        btnPanel.add(saveBtn);

        MaPanel rightPanel = new MaPanel();
        rightPanel.setBorderColor(Macolor.trans);
        rightPanel.setLayout(new BorderLayout(0, 12));
        rightPanel.add(previewScroll, BorderLayout.CENTER);
        rightPanel.add(btnPanel, BorderLayout.SOUTH);

        GroupLayout layout = (GroupLayout) getContentPane().getLayout();
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(18)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(navLabel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(maPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(24)
                            .addComponent(rightPanel, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE)))
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
                        .addComponent(rightPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(18, Short.MAX_VALUE))
        );
        pack();
    }

    private void showPreview() {
        if (currentReceipt == null) return;
        BufferedImage img = currentReceipt.toImage();
        previewLabel.setIcon(new ImageIcon(img));
        previewLabel.revalidate();
    }

    private void saveReceiptImage() {
        if (currentReceipt == null) {
            MaOptionPane.showMessageDialog(this, "ยังไม่มีใบเสร็จให้บันทึก");
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
        navLabel1.setExtraText("ออกใบเสณ้จ");
        maPanel1 = new app.core.components.MaPanel();
        maScrollPane1 = new app.core.components.MaScrollPane();
        maTextArea1 = new app.core.components.MaTextArea();
        maLabel1 = new app.core.components.MaLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        maTextArea1.setText("วิธีการใช้งาน\n** วิธีการเพิ่มข้อมูล**\n\nกดปุ่ม “+ เพิ่มข้อมูล”\n1. ระบบจะเปิดฟอร์มให้กรอกข้อมูล (ขึ้นอยู่กับการออกแบบระบบ)\n2.กรอกข้อมูลให้ครบในแต่ละคอลัมน์\n3.กดยืนยันเพื่อบันทึกข้อมูล\n4.ข้อมูลใหม่จะแสดงในตารางทันที\n\n*หมายเหตุ: ควรตรวจสอบความถูกต้องก่อนบันทึก\n\n\n** วิธีการลบข้อมูล **\n\n1.คลิกเลือกแถวข้อมูลที่ต้องการลบ\n2.กดปุ่ม “ลบข้อมูลที่เลือก”\n\n* คำเตือน: การลบข้อมูลไม่สามารถกู้คืนได้ ควรตรวจสอบก่อนลบทุกครั้ง");
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
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>

        /* Create and display the form */
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
