/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation.roomAndaccessory.controller;

import app.MainFrame;
import app.core.components.MaOptionPane;
import java.util.HashMap;
import java.util.*;
import javax.swing.JOptionPane;
import model.client.PocketBaseClient;
import model.session.SessionManager;
import model.utils.ApiObject;
import presentation.booking.controller.Booking;

/**
 *
 * @author poke
 */
public class Room extends ApiObject {

    private String id;
    private String roomName;
    private double pricePerHour;
    private static PocketBaseClient pb = SessionManager.pb;
    private static PocketBaseClient.PBResponse pbClient;

    public static HashMap<String, Room> data = new HashMap<>();

    public Room(String id, String roomName, double pricePerHour) {
        this.id = id;
        this.roomName = roomName;
        this.pricePerHour = pricePerHour;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return roomName;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    @Override
    public String toJson() {
        return "{\n"
                + "  \"name\": \"" + this.roomName + "\",\n"
                + "  \"price_per_hour\": " + this.pricePerHour + "\n"
                + "}";
    }

    public static void updateRoomData(Room room, String roomName, String pricePerHourStr) {
        room.setRoomName(roomName);

        double pricePerHour = room.pricePerHour;
        try {
            pricePerHour = Double.parseDouble(pricePerHourStr);
        } catch (NumberFormatException ex) {
            MaOptionPane.showMessageDialog(MainFrame.getInstance(), "Price need to be Number");
        }

        room.setPricePerHour(pricePerHour);
    }

    public static void loadRooms(java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) {
            return;
        }

        try {
            PocketBaseClient.PBResponse res = pb.getRecords("java_room");

            if (!res.isOk()) {
                logger.warning("Failed to load rooms: " + res.getStatusCode());
                return;
            }

            List<String> items = res.getItems();

            // clear old data (important)
            Room.data.clear();

            for (String item : items) {

                String id = PocketBaseClient.extractJsonString(item, "id");
                String roomName = PocketBaseClient.extractJsonString(item, "name");

                // price might be number → use extractDouble (or parse)
                double pricePerHour = PocketBaseClient.extractJsonNumber(item, "price_per_hour");

                System.out.println("Room: " + id + " " + roomName + pricePerHour);

                Room.data.put(id, new Room(id, roomName, pricePerHour));
            }
            // refresh UI

        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to load rooms", ex);
        }
    }

    public static HashMap<String, String> getRoomNameToIdMap() {
        HashMap<String, String> result = new HashMap<>();
        // for null id
        for (String roomId : data.keySet()) {
            Room room = data.get(roomId);

            result.put(room.getName(), roomId);
        }

        return result;
    }

    public static ArrayList<String> getRoomNamesList() {
        ArrayList<String> roomNames = new ArrayList<>();

        for (Room room : data.values()) {
            roomNames.add(room.getName());
        }

        return roomNames;
    }

    public static void postRoom(Room room, java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) {
            logger.warning("Not authenticated, cannot post room.");
            return;
        }

        try {
            String jsonPayload = room.toJson();
            PocketBaseClient.PBResponse res = pb.createRecord("java_room", jsonPayload);

            if (!res.isOk()) {
                logger.warning("Failed to post room: " + res.getStatusCode() + " " + res.getBody());
                return;
            }

            String roomId = PocketBaseClient.extractJsonString(res.getBody(), "id");
            room.id = roomId;
            Room.data.put(roomId, room);

            System.out.println("Room posted successfully: ID = " + roomId);

        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to post room", ex);
        }
    }

    public static void updateRoom(Room room, java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) {
            logger.warning("Not authenticated, cannot update room.");
            return;
        }

        try {
            String jsonPayload = room.toJson();
            PocketBaseClient.PBResponse res = pb.updateRecord("java_room", room.getId(), jsonPayload);

            if (!res.isOk()) {
                logger.warning("Failed to update room: " + res.getStatusCode() + " " + res.getBody());
                return;
            }

            Room.data.put(room.getId(), room);
            System.out.println("Room updated successfully: ID = " + room.getId());

        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to update room", ex);
        }
    }

    public static void deleteRoom(String roomId, java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) {
            logger.warning("Not authenticated, cannot delete room.");
            return;
        }

        try {
            // System.out.println(Booking.data.get("zdw04rqxanu69ml").getRoomId());
            for (Booking booking : Booking.data.values()) {
                System.out.println("del room id :" + roomId + "\n" + "booking roomid :" + booking.getRoomId());
                if (roomId.equals(booking.getRoomId())) {
                    MaOptionPane.showMessageDialog(MainFrame.getInstance(), "มีการจองในห้องนี้อยู่โปรดลบการจองก่อน");
                    return;
                }
            }

            PocketBaseClient.PBResponse res = pb.deleteRecord("java_room", roomId);

            if (!res.isOk()) {
                logger.warning("Failed to delete room: " + res.getStatusCode() + " " + res.getBody());
                return;
            }

            Room.data.remove(roomId);
            System.out.println("Room deleted successfully: ID = " + roomId);

        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to delete room", ex);
        }
    }

}
