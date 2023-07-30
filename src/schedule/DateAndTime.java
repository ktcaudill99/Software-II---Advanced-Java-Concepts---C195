/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Katie-BAMF
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

