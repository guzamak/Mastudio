package app;

import app.core.components.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.border.Border;
import presentation.booking.controller.Booking;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author poke
 */
public class TimeNotificationFrame extends MaInternalFrame {

    private MaLabel timeLabel;
    private HashSet<String> notified = new HashSet<>();

    public TimeNotificationFrame() {
        setSize(200, 80);
        setLayout(new BorderLayout());

        setClosable(false);
        setTitle("เเจ้งเตือนเวลา");

        JPanel panel = new JPanel(new BorderLayout());
        Border line = BorderFactory.createLineBorder(Macolor.magreen, 1);
        setBorder(line);
        timeLabel = new MaLabel();
        timeLabel.setText("00:00:00");
        timeLabel.setForeground(Macolor.magreen);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(timeLabel, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);

        startThread();
    }

    private void startThread() {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    LocalDateTime now = LocalDateTime.now();

                    String time = now.format(
                            DateTimeFormatter.ofPattern("HH:mm:ss")
                    );

                    timeLabel.setText(time);
                    checkBookings(now);

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        t.setDaemon(true);
        t.start();
    }

    private LocalDateTime getEndDateTime(Booking b) {
        try {
            String rawDate = b.getCheckIn(); // "2023-03-01 00:00:00.000Z"
            String date = rawDate.substring(0, 10); // "2023-03-01"

            String slot = b.getTimeSlot(); // "11.00-04.00"
            String endTime = slot.split("-")[1].replace(".", ":"); // "04:00"

//            System.out.println(date + " " + endTime);

            return LocalDateTime.parse(
                    date + " " + endTime,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void checkBookings(LocalDateTime now) {
        for (Booking b : Booking.data.values()) {

            LocalDateTime start = getEndDateTime(b);
//            System.out.println(start);
            if (start == null) {
                continue;
            }
//            System.out.println("in1");
            if (!notified.contains(b.getId())
                    && now.getYear() == start.getYear()
                    && now.getDayOfYear() == start.getDayOfYear()
                    && now.getHour() == start.getHour()
                    && now.getMinute() == start.getMinute()) {
//                System.out.println("in2");
                notified.add(b.getId());

                MaOptionPane.showMessageDialog(MainFrame.getInstance(), "หมดเวลาที่ห้อง : " + b.getRoom());
            }
        }
    }

}
