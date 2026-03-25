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
import model.client.PocketBaseClient;
import model.session.SessionManager;
import model.utils.ApiObject;
import model.utils.TimeUtils;
import presentation.roomAndaccessory.controller.Room;

public class Booking extends ApiObject {

    private static PocketBaseClient pb = SessionManager.pb;
    private static PocketBaseClient.PBResponse pbClient;

    private String room, customer, id;
    private String roomId;
    private String timeSlot;
    private String checkIn;
    private List<String> accessoryIds = new ArrayList<>();
    public static HashMap<String, Booking> data = new HashMap<>();
    public static ArrayList<String> time_slot_list = new ArrayList<>(List.of("10.00-11.00", "11.00-12.00", "12.00-13.00", "13.00-14.00", "14.00-15.00", "15.00-16.00", "16.00-17.00", "17.00-18.00", "18.00-19.00", "19.00-20.00"));

    public Booking(String id, String room, String customer, String timeSlot, String checkIn, String roomId) {
        this.id = id;
        this.roomId = roomId;
        this.room = room;
        this.customer = customer;
        this.timeSlot = timeSlot;
        this.checkIn = checkIn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<String> getAccessoryIds() {
        return accessoryIds;
    }

    public void setAccessoryIds(List<String> accessoryIds) {
        this.accessoryIds = accessoryIds != null ? accessoryIds : new ArrayList<>();
    }

    public static void loadBookings(java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) {
            return;
        }
        try {
            PocketBaseClient.PBResponse res = pb.getRecords("java_book");
            if (!res.isOk()) {
                logger.warning("Failed to load bookings: " + res.getStatusCode());
                return;
            }
            Booking.data.clear();
            for (String item : res.getItems()) {
                String id = PocketBaseClient.extractJsonString(item, "id");
                String customerName = PocketBaseClient.extractJsonString(item, "customer_name");
                String checkIn = PocketBaseClient.extractJsonString(item, "checkIn_time");
                String timeslot = PocketBaseClient.extractJsonString(item, "time_slot");
                String roomId = PocketBaseClient.extractJsonString(item, "room");
                String roomName = Room.data.get(roomId) != null ? Room.data.get(roomId).getName() : roomId;
                List<String> accIds = PocketBaseClient.extractJsonArray(item, "accessories");
                Booking b = new Booking(id, roomName, customerName, timeslot, checkIn, roomId);
                b.setAccessoryIds(accIds);
                Booking.data.put(id, b);
            }
        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to load bookings", ex);
        }
    }
    

    public static void updateBookingData(Booking booking, String room, String id, String customerName, String timeSlot,
            String checkInYearStr, String checkInMonthStr, String checkInDayStr
    ) {
        booking.setRoom(room);
        booking.setRoomId(id);
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

    @Override
    public String toJson() {
        StringBuilder accArray = new StringBuilder("[");
        for (int i = 0; i < accessoryIds.size(); i++) {
            if (i > 0) {
                accArray.append(",");
            }
            accArray.append("\"").append(accessoryIds.get(i)).append("\"");
        }
        accArray.append("]");

        return "{\n"
                + "  \"customer_name\": \"" + this.customer + "\",\n"
                + "  \"checkIn_time\": \"" + this.checkIn + "\",\n"
                + "  \"room\": \"" + this.roomId + "\",\n"
                + "  \"time_slot\": \"" + this.timeSlot + "\",\n"
                + "  \"accessories\": " + accArray + "\n"
                + "}";
    }

    public static void postBooking(Booking booking, java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) {
            logger.warning("Not authenticated, cannot post booking.");
            return;
        }

        try {
            String jsonPayload = booking.toJson();
            System.out.println(jsonPayload);
            PocketBaseClient.PBResponse res = pb.createRecord("java_book", jsonPayload);

            if (!res.isOk()) {
                logger.warning("Failed to post booking: " + res.getStatusCode() + " " + res.getBody());
                return;
            }

            String bookingId = PocketBaseClient.extractJsonString(res.getBody(), "id");
            System.out.println("Booking posted successfully: ID = " + bookingId);
            booking.setId(bookingId);
            Booking.data.put(bookingId, booking);

        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to post booking", ex);
        }
    }

    public static void putBooking(Booking booking, java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) {
            logger.warning("Not authenticated, cannot post booking.");
            return;
        }

        try {
            String jsonPayload = booking.toJson();
            System.out.println(jsonPayload);
            PocketBaseClient.PBResponse res = pb.updateRecord("java_book", booking.id, jsonPayload);

            if (!res.isOk()) {
                logger.warning("Failed to put booking: " + res.getStatusCode() + " " + res.getBody());
                return;
            }

            System.out.println("Booking put successfully: ID = " + booking.id);
            Booking.data.put(booking.id, booking);

        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to post booking", ex);
        }
    }

    public static void loadBookings(java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) return;
        try {
            PocketBaseClient.PBResponse res = pb.getRecords("java_book");
            if (!res.isOk()) {
                logger.warning("Failed to load bookings: " + res.getStatusCode());
                return;
            }
            Booking.data.clear();
            for (String item : res.getItems()) {
                String id = PocketBaseClient.extractJsonString(item, "id");
                String customerName = PocketBaseClient.extractJsonString(item, "customer_name");
                String checkIn = PocketBaseClient.extractJsonString(item, "checkIn_time");
                String timeslot = PocketBaseClient.extractJsonString(item, "time_slot");
                String roomId = PocketBaseClient.extractJsonString(item, "room");
                String roomName = Room.data.get(roomId) != null ? Room.data.get(roomId).getName() : roomId;
                List<String> accIds = PocketBaseClient.extractJsonArray(item, "accessories");
                Booking b = new Booking(id, roomName, customerName, timeslot, checkIn, roomId);
                b.setAccessoryIds(accIds);
                Booking.data.put(id, b);
            }
        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to load bookings", ex);
        }
    }

    public static void deleteBooking(String bookingId, java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) {
            logger.warning("Not authenticated, cannot delete booking.");
            return;
        }

        try {
            PocketBaseClient.PBResponse res = pb.deleteRecord("java_book", bookingId);

            if (!res.isOk()) {
                logger.warning("Failed to delete booking: " + res.getStatusCode() + " " + res.getBody());
                return;
            }

            System.out.println("Booking deleted successfully: ID = " + bookingId);

            Booking.data.remove(bookingId);

        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to delete booking", ex);
        }
    }

}
