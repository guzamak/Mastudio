/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.util;

/**
 *
 * @author poke
 */
import java.time.*;

public class TimeUtils {

    public static String[] convertDateToUtcRange(LocalDate date, ZoneId zone) {
        ZonedDateTime startZdt = date.atStartOfDay(zone);
        ZonedDateTime endZdt = date.plusDays(1).atStartOfDay(zone);

        // Convert to UTC because pocketbse use utc
        Instant startUtc = startZdt.toInstant();
        Instant endUtc = endZdt.toInstant();
        return new String[]{startUtc.toString(), endUtc.toString()};
    }
}