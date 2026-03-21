/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation.booking.controller;

/**
 *
 * @author poke
 */
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import model.client.TimeUtils;
public class Booking {
    private String room,customer ,id;
    private String checkIn,checkout;
    public static HashMap<String, Booking> data = new HashMap<>();

    public Booking(String id,String room, String customer, String checkIn, String checkout) {
        this.id = id;
        this.room = room;
        this.customer = customer;
        this.checkIn = checkIn;
        this.checkout = checkout;
    }

    public String getId() {
        return id;
    }

    
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }
    
        public static void updateBookingData(
            Booking booking,
            String room , String customerName,
            String checkInYearStr, String checkInMonthStr, String checkInDayStr, String checkInHourStr, String checkInMinuteStr,
            String checkOutYearStr, String checkOutMonthStr, String checkOutDayStr, String checkOutHourStr, String checkOutMinuteStr
    ) {
        // Parse check-in
        int checkInYear = Integer.parseInt(checkInYearStr);
        int checkInMonth = TimeUtils.parseMonth(checkInMonthStr);
        int checkInDay = Integer.parseInt(checkInDayStr);
        int checkInHour = Integer.parseInt(checkInHourStr);
        int checkInMinute = Integer.parseInt(checkInMinuteStr);

        LocalDateTime checkInDateTime = LocalDateTime.of(checkInYear, checkInMonth, checkInDay, checkInHour, checkInMinute);

        // Parse check-out
        int checkOutYear = Integer.parseInt(checkOutYearStr);
        int checkOutMonth = TimeUtils.parseMonth(checkOutMonthStr);
        int checkOutDay = Integer.parseInt(checkOutDayStr);
        int checkOutHour = Integer.parseInt(checkOutHourStr);
        int checkOutMinute = Integer.parseInt(checkOutMinuteStr);

        LocalDateTime checkOutDateTime = LocalDateTime.of(checkOutYear, checkOutMonth, checkOutDay, checkOutHour, checkOutMinute);

        // Convert to ISO string with UTC offset
        OffsetDateTime checkInOffset = checkInDateTime.atOffset(ZoneOffset.UTC);
        OffsetDateTime checkOutOffset = checkOutDateTime.atOffset(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSX");

        booking.setCheckIn(checkInOffset.format(formatter));
        booking.setCheckout(checkOutOffset.format(formatter));
    }


    
}
