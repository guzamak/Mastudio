package app.model.client;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PocketBaseClient {

    private final String baseUrl;
    private final HttpClient http;
    private String authToken;

    public PocketBaseClient(String baseUrl) {
        this.baseUrl = baseUrl.replaceAll("/+$", "");
        this.http = HttpClient.newHttpClient();
    }

    // --- Authentication ---

    public PBResponse authWithPassword(String collection, String identity, String password)
            throws IOException, InterruptedException {
        String json = toJson(Map.of(
                "identity", identity,
                "password", password
        ));
        PBResponse res = post("/api/collections/" + collection + "/auth-with-password", json);
        if (res.isOk()) {
            this.authToken = res.getJsonString("token");
        }
        return res;
    }

    public boolean isAuthenticated() {
        return authToken != null && !authToken.isEmpty();
    }

    public void clearAuth() {
        this.authToken = null;
    }

    public String getAuthToken() {
        return authToken;
    }

    // --- CRUD ---

    public PBResponse getRecords(String collection) throws IOException, InterruptedException {
        return get("/api/collections/" + collection + "/records");
    }

    public PBResponse getRecords(String collection, Map<String, String> queryParams)
            throws IOException, InterruptedException {
        String qs = queryParams.entrySet().stream()
                .map(e -> encodeParam(e.getKey()) + "=" + encodeParam(e.getValue()))
                .collect(Collectors.joining("&"));
        return get("/api/collections/" + collection + "/records?" + qs);
    }

    public PBResponse getRecord(String collection, String recordId)
            throws IOException, InterruptedException {
        return get("/api/collections/" + collection + "/records/" + recordId);
    }

    public PBResponse createRecord(String collection, String jsonBody)
            throws IOException, InterruptedException {
        return post("/api/collections/" + collection + "/records", jsonBody);
    }

    public PBResponse updateRecord(String collection, String recordId, String jsonBody)
            throws IOException, InterruptedException {
        return patch("/api/collections/" + collection + "/records/" + recordId, jsonBody);
    }

    public PBResponse deleteRecord(String collection, String recordId)
            throws IOException, InterruptedException {
        return delete("/api/collections/" + collection + "/records/" + recordId);
    }

    // --- Low-level HTTP methods ---

    public PBResponse get(String path) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .GET();
        return send(builder);
    }

    public PBResponse post(String path, String jsonBody) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody));
        return send(builder);
    }

    public PBResponse patch(String path, String jsonBody) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody));
        return send(builder);
    }

    public PBResponse delete(String path) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .DELETE();
        return send(builder);
    }

    private PBResponse send(HttpRequest.Builder builder) throws IOException, InterruptedException {
        builder.header("Content-Type", "application/json");
        if (authToken != null) {
            builder.header("Authorization", authToken);
        }
        HttpResponse<String> response = http.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        return new PBResponse(response.statusCode(), response.body());
    }

    // --- JSON helpers (no external library needed) ---

    public static String toJson(Map<String, String> fields) {
        String body = fields.entrySet().stream()
                .map(e -> "\"" + escapeJson(e.getKey()) + "\":\"" + escapeJson(e.getValue()) + "\"")
                .collect(Collectors.joining(","));
        return "{" + body + "}";
    }

    private static String escapeJson(String value) {
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }

    private static String encodeParam(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    /**
     * Extract a string value by key from a raw JSON string.
     * Usable on individual items returned by PBResponse.getItems().
     */
    public static String extractJsonString(String json, String key) {
        Pattern p = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"([^\"\\\\]*(?:\\\\.[^\"\\\\]*)*)\"");
        Matcher m = p.matcher(json);
        return m.find() ? m.group(1).replace("\\\"", "\"").replace("\\\\", "\\") : null;
    }

    // --- Response wrapper ---

    public static class PBResponse {
        private final int statusCode;
        private final String body;

        public PBResponse(int statusCode, String body) {
            this.statusCode = statusCode;
            this.body = body;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getBody() {
            return body;
        }

        public boolean isOk() {
            return statusCode >= 200 && statusCode < 300;
        }

        public String getJsonString(String key) {
            return extractJsonString(body, key);
        }

        /**
         * Parse the "items" array from a PocketBase list response.
         * Each element is the raw JSON string of one record.
         */
        public List<String> getItems() {
            int arrStart = body.indexOf("\"items\"");
            if (arrStart == -1) return List.of();
            int bracketOpen = body.indexOf('[', arrStart);
            if (bracketOpen == -1) return List.of();

            List<String> items = new ArrayList<>();
            int depth = 0;
            int objStart = -1;
            for (int i = bracketOpen; i < body.length(); i++) {
                char c = body.charAt(i);
                if (c == '{') {
                    if (depth == 0) objStart = i;
                    depth++;
                } else if (c == '}') {
                    depth--;
                    if (depth == 0 && objStart != -1) {
                        items.add(body.substring(objStart, i + 1));
                        objStart = -1;
                    }
                } else if (c == ']' && depth == 0) {
                    break;
                }
            }
            return items;
        }

        @Override
        public String toString() {
            return "PBResponse{status=" + statusCode + ", body=" + body + "}";
        }
    }
}
