
package main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The {@code DateAndTime} class provides utility methods to retrieve the current date and time
 * formatted according to the system's default time zone.
 * <p>
 * It includes methods to get the current timestamp as a formatted string and the current date as a SQL date object.
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
    public static LocalDateTime convertToLocalDateTimeFromUTC(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZoneId systemZoneId = ZoneId.systemDefault();
        return dateTime.atZone(utcZoneId).withZoneSameInstant(systemZoneId).toLocalDateTime();
    }

    public static LocalDateTime convertToUTCFromLocalDateTime(LocalDateTime localDateTime) {
        ZoneId systemZoneId = ZoneId.systemDefault();
        ZoneId utcZoneId = ZoneId.of("UTC");
        return localDateTime.atZone(systemZoneId).withZoneSameInstant(utcZoneId).toLocalDateTime();
    }

    public static LocalDateTime convertToETFromLocalDateTime(LocalDateTime localDateTime) {
        ZoneId systemZoneId = ZoneId.systemDefault();
        ZoneId etZoneId = ZoneId.of("America/New_York");
        return localDateTime.atZone(systemZoneId).withZoneSameInstant(etZoneId).toLocalDateTime();
    }

    public static LocalDateTime convertToLocalDateTimeFromET(LocalDateTime etDateTime) {
        ZoneId etZoneId = ZoneId.of("America/New_York");
        ZoneId systemZoneId = ZoneId.systemDefault();
        return etDateTime.atZone(etZoneId).withZoneSameInstant(systemZoneId).toLocalDateTime();
    }

}

