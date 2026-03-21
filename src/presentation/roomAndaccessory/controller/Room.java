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

    public static HashMap<String, Room> data = new HashMap<>();
    public Room(String id, String roomName) {
        this.id = id;
        this.roomName = roomName;
    }

    public String getId() { return id; }
    public String getRoomName() { return roomName; }
    
}
