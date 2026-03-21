/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation.roomAndaccessory.controller;

import java.util.HashMap;

/**
 *
 * @author poke
 */
public class Room {

    private String id;
    private String roomName;
    private double pricePerHour;

    public static HashMap<String, Room> data = new HashMap<>();

    public Room(String id, String roomName, double pricePerHour) {
        this.id = id;
        this.roomName = roomName;
        this.pricePerHour = pricePerHour;
    }

    public String getId() {
        return id;
    }

    public String getRoomName() {
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

    
}
