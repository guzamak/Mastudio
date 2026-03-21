/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation.roomAndaccessory.controller;

/**
 *
 * @author poke
 */

import java.util.HashMap;
import java.util.List;
import model.client.PocketBaseClient;
import model.session.SessionManager;

public class Accessory {

    private String id;
    private String name;
    private double pricePerHour;

    private static PocketBaseClient pb = SessionManager.pb;

    public static HashMap<String, Accessory> data = new HashMap<>();

    // Constructor
    public Accessory(String id, String name, double pricePerHour) {
        this.id = id;
        this.name = name;
        this.pricePerHour = pricePerHour;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    // Load data from PocketBase
    public static void loadAccessories(java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) {
            return;
        }

        try {
            PocketBaseClient.PBResponse res = pb.getRecords("java_accessory");

            if (!res.isOk()) {
                logger.warning("Failed to load accessories: " + res.getStatusCode());
                return;
            }

            List<String> items = res.getItems();

            // clear old data
            Accessory.data.clear();

            for (String item : items) {

                String id = PocketBaseClient.extractJsonString(item, "id");
                String name = PocketBaseClient.extractJsonString(item, "name");

                double pricePerHour = PocketBaseClient.extractJsonNumber(item, "price_per_hour");

                System.out.println("Accessory: " + id + " " + name + " " + pricePerHour);

                Accessory.data.put(id, new Accessory(id, name, pricePerHour));
            }

        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to load accessories", ex);
        }
    }

    // Optional (for JComboBox display)
    @Override
    public String toString() {
        return name + " (" + pricePerHour + "/hr)";
    }
}