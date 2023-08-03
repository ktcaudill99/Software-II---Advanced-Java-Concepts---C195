
package main;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The {@code DateAndTime} class provides utility methods to retrieve the current date and time
 * formatted according to the system's default time zone.
 * <p>
 * It includes methods to get the current timestamp as a formatted string and the current date as a SQL date object.
 *
 * @author Katherine Caudill
 */
public class DateAndTime {

    /**
     * Returns the current date and time as a string, formatted as "yyyy-MM-dd HH:mm:ss" in the system's default time zone.
     *
     * @return A string representing the current timestamp.
     */
    public static String getTimeStamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId zoneId = ZoneId.systemDefault(); // or ZoneId.of("ZoneId") for a specific time zone
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        return now.format(formatter);
    }

    /**
     * Returns the current date as a {@code java.sql.Date} object.
     *
     * @return A {@code java.sql.Date} object representing the current date.
     */
    public static java.sql.Date getDate() {
        java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());
        return date;
    }
}

