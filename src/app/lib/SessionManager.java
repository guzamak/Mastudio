package app.lib;

import java.util.prefs.Preferences;

/**
 * Persists login session to disk using java.util.prefs.Preferences.
 * Survives app restarts — no need to re-login until explicit logout.
 */
public class SessionManager {

    private static final Preferences prefs = Preferences.userNodeForPackage(SessionManager.class);

    private static final String KEY_USERNAME = "session_username";
    private static final String KEY_TOKEN = "session_token";
    private static final String KEY_LOGGED_IN = "session_logged_in";

    public static void saveSession(String username, String token) {
        prefs.put(KEY_USERNAME, username);
        prefs.put(KEY_TOKEN, token);
        prefs.putBoolean(KEY_LOGGED_IN, true);
    }

    public static boolean hasSession() {
        return prefs.getBoolean(KEY_LOGGED_IN, false)
                && !prefs.get(KEY_TOKEN, "").isEmpty();
    }

    public static String getUsername() {
        return prefs.get(KEY_USERNAME, "");
    }

    public static String getToken() {
        return prefs.get(KEY_TOKEN, "");
    }

    public static void clearSession() {
        prefs.remove(KEY_USERNAME);
        prefs.remove(KEY_TOKEN);
        prefs.putBoolean(KEY_LOGGED_IN, false);
    }
}
