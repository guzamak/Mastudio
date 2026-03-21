/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.client;

/**
 *
 * @author poke
 */
import java.time.*;
import java.util.HashMap;

public class TimeUtils {

    public static String[] years;
    public static String[] months;
    public static String[] days;
    public static String[] hours;
    public static String[] minutes;
    public static HashMap<String, String> monthMap = new HashMap<>();

    public static String[] convertDateToUtcRange(LocalDate date, ZoneId zone) {
        ZonedDateTime startZdt = date.atStartOfDay(zone);
        ZonedDateTime endZdt = date.plusDays(1).atStartOfDay(zone);

        // Convert to UTC because pocketbse use utc
        Instant startUtc = startZdt.toInstant();
        Instant endUtc = endZdt.toInstant();
        return new String[]{startUtc.toString(), endUtc.toString()};
    }

    static {
        // Years: current year ± 5 years
        int currentYear = Year.now().getValue();
        years = new String[11];
        for (int i = 0; i < 11; i++) {
            years[i] = String.valueOf(currentYear - 5 + i);
        }

        // Months: 1-12
        months = new String[12];
        String[] monthNames = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        for (int i = 0; i < 12; i++) {
            months[i] = String.valueOf(i + 1);
            monthMap.put(String.valueOf(i + 1), monthNames[i]);  // map number -> name
        }

        // Days: 1-31
        days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.valueOf(i + 1);
        }

        // Hours: 0-23
        hours = new String[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = String.format("%02d", i);
        }

        // Minutes: 0-59
        minutes = new String[60];
        for (int i = 0; i < 60; i++) {
            minutes[i] = String.format("%02d", i);
        }
    }

    public static int parseMonth(String monthStr) {
        try {
            return Integer.parseInt(monthStr);
        } catch (NumberFormatException e) {
            for (String key : monthMap.keySet()) {
                if (monthMap.get(key).equalsIgnoreCase(monthStr)) {
                    return Integer.parseInt(key);
                }
            }
        }
        return 1;
    }
}
