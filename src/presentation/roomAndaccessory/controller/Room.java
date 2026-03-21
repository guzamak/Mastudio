/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation.roomAndaccessory.controller;

import java.util.HashMap;
import java.util.*;
import model.client.PocketBaseClient;
import model.session.SessionManager;

/**
 *
 * @author poke
 */
public class Room {

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

}
