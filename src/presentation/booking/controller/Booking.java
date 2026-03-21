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
import java.util.List;
import model.client.TimeUtils;

public class Booking {

    private String room, customer, id;
    private String roomId;
    private String timeSlot;
    private String checkIn;
    public static HashMap<String, Booking> data = new HashMap<>();
    public static ArrayList<String> time_slot_list = new ArrayList<>(List.of("10.00-11.00","11.00-12.00","12.00-13.00","13.00-14.00","14.00-15.00","15.00-16.00","16.00-17.00","17.00-18.00","18.00-19.00","19.00-20.00"));

    public Booking(String id, String room, String customer, String timeSlot, String checkIn) {
        this.id = id;
        this.room = room;
        this.customer = customer;
        this.timeSlot = timeSlot;
        this.checkIn = checkIn;
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

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public static void updateBookingData(Booking booking,String room,String customerName,String timeSlot,
            String checkInYearStr, String checkInMonthStr, String checkInDayStr
            ){
        booking.setRoom(room);
        booking.setCustomer(customerName);
        booking.setTimeSlot(timeSlot);
        int checkInYear = Integer.parseInt(checkInYearStr);
        int checkInMonth = TimeUtils.parseMonth(checkInMonthStr);
        int checkInDay = Integer.parseInt(checkInDayStr);
        int checkInHour = 0;
        int checkInMinute = 0;

        LocalDateTime checkInDateTime = LocalDateTime.of(checkInYear, checkInMonth, checkInDay, checkInHour, checkInMinute);
        OffsetDateTime checkInOffset = checkInDateTime.atOffset(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSX");

        booking.setCheckIn(checkInOffset.format(formatter));

    }
}

