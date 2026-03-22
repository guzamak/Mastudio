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
import model.utils.ApiObject;

public class Accessory extends ApiObject {

    private String id;
    private String name;
    private double pricePerHour;

    private static PocketBaseClient pb = SessionManager.pb;

    public static HashMap<String, Accessory> data = new HashMap<>();

    public Accessory(String id, String name, double pricePerHour) {
        this.id = id;
        this.name = name;
        this.pricePerHour = pricePerHour;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

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

    @Override
    public String toJson() {
        // Match the PocketBase field IDs exactly
        return "{\n"
                + "  \"name\": \"" + this.name + "\",\n"
                + "  \"price_per_hour\": " + this.pricePerHour + "\n"
                + "}";
    }

    public static void postAccessory(Accessory accessory, java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) {
            logger.warning("Not authenticated, cannot post accessory.");
            return;
        }

        try {
            String jsonPayload = accessory.toJson();
            PocketBaseClient.PBResponse res = pb.createRecord("java_accessory", jsonPayload);

            if (!res.isOk()) {
                logger.warning("Failed to post accessory: " + res.getStatusCode() + " " + res.getBody());
                return;
            }

            String accessoryId = PocketBaseClient.extractJsonString(res.getBody(), "id");
            accessory.id = accessoryId;
            Accessory.data.put(accessoryId, accessory);

            System.out.println("Accessory posted successfully: ID = " + accessoryId);

        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to post accessory", ex);
        }
    }

    public static void updateAccessory(Accessory accessory, java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) {
            logger.warning("Not authenticated, cannot update accessory.");
            return;
        }

        try {
            String jsonPayload = accessory.toJson();
            PocketBaseClient.PBResponse res = pb.updateRecord("java_accessory", accessory.getId(), jsonPayload);

            if (!res.isOk()) {
                logger.warning("Failed to update accessory: " + res.getStatusCode() + " " + res.getBody());
                return;
            }

            Accessory.data.put(accessory.getId(), accessory);
            System.out.println("Accessory updated successfully: ID = " + accessory.getId());

        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to update accessory", ex);
        }
    }

    public static void deleteAccessory(String accessoryId, java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) {
            logger.warning("Not authenticated, cannot delete accessory.");
            return;
        }

        try {

            PocketBaseClient.PBResponse res = pb.deleteRecord("java_accessory", accessoryId);

            if (!res.isOk()) {
                logger.warning("Failed to delete accessory: " + res.getStatusCode() + " " + res.getBody());
                return;
            }

            Accessory.data.remove(accessoryId);
            System.out.println("Accessory deleted successfully: ID = " + accessoryId);

        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to delete accessory", ex);
        }
    }
}
