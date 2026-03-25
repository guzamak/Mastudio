package presentation.payment.view;

import app.core.components.*;
import presentation.booking.controller.Booking;
import presentation.payment.controller.Payment;
import presentation.roomAndaccessory.controller.Room;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class PaymentHistoryFrame extends MaInternalFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(PaymentHistoryFrame.class.getName());

    private MaTable paymentTable;
    private ArrayList<Payment> paymentList = new ArrayList<>();

    public PaymentHistoryFrame() {
        setTitle("ประวัติการโอนเงิน");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setupUI();
        loadData();
    }

    private void setupUI() {
        NavLabel navLabel = new NavLabel();
        navLabel.setExtraText("ประวัติการโอนเงิน");

        paymentTable = new MaTable();

        JScrollPane tableScroll = new JScrollPane(paymentTable);
        tableScroll.setBorder(BorderFactory.createEmptyBorder());
        tableScroll.getViewport().setBackground(Color.WHITE);

        MaButton refreshBtn = new MaButton();
        refreshBtn.setText("Refresh");
        refreshBtn.setArc(16);
        refreshBtn.setButtonColor(Macolor.mablue);
        refreshBtn.addActionListener(e -> loadData());

        MaButton toggleBtn = new MaButton();
        toggleBtn.setText("เปลี่ยนสถานะ (paid ↔ pending)");
        toggleBtn.setArc(16);
        toggleBtn.setButtonColor(Macolor.magreen);
        toggleBtn.addActionListener(e -> toggleStatus());

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(refreshBtn);
        btnPanel.add(toggleBtn);

        MaPanel contentPanel = new MaPanel();
        contentPanel.setBorderColor(Macolor.magreen);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BorderLayout(0, 12));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        contentPanel.add(tableScroll, BorderLayout.CENTER);
        contentPanel.add(btnPanel, BorderLayout.SOUTH);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        getContentPane().add(navLabel);
        getContentPane().add(contentPanel);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(18)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(navLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, 700, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(navLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(12)
                    .addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, 420, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(18, Short.MAX_VALUE))
        );
        pack();
    }

    public void loadData() {
        Room.loadRooms(logger);
        Booking.loadBookings(logger);
        Payment.loadPayments(logger);
        paymentList.clear();

        ArrayList<String> columns = new ArrayList<>();
        columns.add("ลูกค้า");
        columns.add("ห้อง");
        columns.add("Time Slot");
        columns.add("ยอดรวม (บาท)");
        columns.add("วันที่จ่าย");
        columns.add("สถานะ");

        ArrayList<Object[]> rows = new ArrayList<>();
        for (Payment p : Payment.data.values()) {
            paymentList.add(p);

            Booking b = Booking.data.get(p.getBookingId());
            String customer = b != null ? b.getCustomer() : "-";
            String room     = b != null ? b.getRoom()     : "-";
            String timeSlot = b != null ? b.getTimeSlot() : "-";

            String paidAt = p.getPaidAt();
            if (paidAt != null && paidAt.length() >= 10) paidAt = paidAt.substring(0, 10);

            rows.add(new Object[]{
                customer,
                room,
                timeSlot,
                String.format("%.2f", p.getAmount()),
                paidAt,
                p.getStatus()
            });
        }
        paymentTable.updateView(columns, rows);
    }

    private void toggleStatus() {
        int row = paymentTable.getSelectedRow();
        if (row < 0 || row >= paymentList.size()) {
            MaOptionPane.showMessageDialog(this, "กรุณาเลือกรายการก่อน");
            return;
        }
        Payment p = paymentList.get(row);
        p.setStatus(p.getStatus().equals("paid") ? "pending" : "paid");
        Payment.updateStatus(p, logger);
        loadData();
    }
}
