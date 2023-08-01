
package main;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class contains methods for getting the current date and time in the user's time zone.
 */
public class DateAndTime {

    public static String getTimeStamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId zoneId = ZoneId.systemDefault(); // or ZoneId.of("ZoneId") for a specific time zone
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        return now.format(formatter);
    }

    public static java.sql.Date getDate() {
        java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());
        return date;
    }
}

