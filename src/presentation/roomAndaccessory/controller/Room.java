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
    private String name ;
    public static HashMap<String, Room> data = new HashMap<>();

    public void setName(String name) {
        this.name = name;
    }
    
    
}
