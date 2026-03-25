package presentation.payment.controller;

import model.client.PocketBaseClient;
import model.session.SessionManager;
import model.utils.ApiObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Payment extends ApiObject {

    private static final PocketBaseClient pb = SessionManager.pb;
    public static HashMap<String, Payment> data = new HashMap<>();

    private String id;
    private String bookingId;
    private double amount;
    private String paidAt;
    private String status; // "paid" / "pending"

    public Payment(String id, String bookingId, double amount, String paidAt, String status) {
        this.id = id;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paidAt = paidAt;
        this.status = status;
    }

    // --- Getters / Setters ---

    public String getId() { return id; }
    public String getBookingId() { return bookingId; }
    public double getAmount() { return amount; }
    public String getPaidAt() { return paidAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // --- toJson ---

    @Override
    public String toJson() {
        return "{\n"
                + "  \"booking\": \"" + bookingId + "\",\n"
                + "  \"amount\": " + amount + ",\n"
                + "  \"paid_at\": \"" + paidAt + "\",\n"
                + "  \"status\": \"" + status + "\"\n"
                + "}";
    }

    public String toUpdateStatusJson() {
        return "{ \"status\": \"" + status + "\" }";
    }

    // --- Static DAO methods ---

    public static void loadPayments(java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) return;
        try {
            PocketBaseClient.PBResponse res = pb.getRecords("java_payment");
            if (!res.isOk()) {
                logger.warning("Failed to load payments: " + res.getStatusCode());
                return;
            }
            Payment.data.clear();
            for (String item : res.getItems()) {
                String id       = PocketBaseClient.extractJsonString(item, "id");
                String bookingId = PocketBaseClient.extractJsonString(item, "booking");
                Double amount   = PocketBaseClient.extractJsonNumber(item, "amount");
                String paidAt   = PocketBaseClient.extractJsonString(item, "paid_at");
                String status   = PocketBaseClient.extractJsonString(item, "status");
                Payment.data.put(id, new Payment(id, bookingId,
                        amount != null ? amount : 0.0,
                        paidAt != null ? paidAt : "",
                        status != null ? status : "pending"));
            }
        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to load payments", ex);
        }
    }

    public static Payment postPayment(String bookingId, double amount, java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) {
            logger.warning("Not authenticated, cannot post payment.");
            return null;
        }
        try {
            String paidAt = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Payment payment = new Payment(null, bookingId, amount, paidAt, "paid");
            PocketBaseClient.PBResponse res = pb.createRecord("java_payment", payment.toJson());
            if (!res.isOk()) {
                logger.warning("Failed to post payment: " + res.getStatusCode() + " " + res.getBody());
                return null;
            }
            String newId = PocketBaseClient.extractJsonString(res.getBody(), "id");
            payment.id = newId;
            Payment.data.put(newId, payment);
            return payment;
        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to post payment", ex);
            return null;
        }
    }

    public static void updateStatus(Payment payment, java.util.logging.Logger logger) {
        if (!pb.isAuthenticated()) return;
        try {
            PocketBaseClient.PBResponse res = pb.updateRecord("java_payment", payment.id, payment.toUpdateStatusJson());
            if (!res.isOk()) {
                logger.warning("Failed to update payment status: " + res.getStatusCode());
                return;
            }
            Payment.data.put(payment.id, payment);
        } catch (java.io.IOException | InterruptedException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to update payment", ex);
        }
    }
}
