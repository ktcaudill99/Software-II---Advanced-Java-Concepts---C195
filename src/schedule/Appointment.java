/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import com.mysql.cj.conf.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Katie-BAMF
 */
public class Appointment {

    private final SimpleIntegerProperty aptId = new SimpleIntegerProperty();
    private final SimpleIntegerProperty aptCustId = new SimpleIntegerProperty();
    private final SimpleStringProperty aptStart = new SimpleStringProperty();
    private final SimpleStringProperty aptEnd = new SimpleStringProperty();
    private final SimpleStringProperty aptTitle = new SimpleStringProperty();
    private final SimpleStringProperty aptDescription = new SimpleStringProperty();
    private final SimpleStringProperty aptLocation = new SimpleStringProperty();
    private final SimpleStringProperty aptContact = new SimpleStringProperty();

    public Appointment() {
    }

    public Appointment(int id, int custId, String start, String end, String title, String description, String location, String contact) {
        this.setAptId(id);
        this.setAptCustId(custId);
        this.setAptStart(start);
        this.setAptEnd(end);
        this.setAptTitle(title);
        this.setAptDescription(description);
        this.setAptLocation(location);
        this.setAptContact(contact);
    }


    public int getAptId() {
        return this.aptId.get();
    }

    public int getAptCustId() {
        return this.aptCustId.get();
    }

    public String getAptEnd() {
        return this.aptEnd.get();
    }

    public String getAptStart() {
        return this.aptStart.get();
    }

    public String getAptTitle() {
        return this.aptTitle.get();
    }

    public String getAptDescription() {
        return this.aptDescription.get();
    }

    public String getAptLocation() {
        return this.aptLocation.get();
    }

    public String getAptContact() {
        return this.aptContact.get();
    }

    public void setAptId(int aptId) {
        this.aptId.set(aptId);
    }

    public void setAptCustId(int aptCustId) {
        this.aptCustId.set(aptCustId);
    }

    public void setAptEnd(String aptEnd) {
        this.aptEnd.set(aptEnd);
    }

    public void setAptStart(String aptTimeStart) {
        this.aptStart.set(aptTimeStart);
    }

    public void setAptTitle(String aptTitle) {
        this.aptTitle.set(aptTitle);
    }

    public void setAptDescription(String aptDescription) {
        this.aptDescription.set(aptDescription);
    }

    public void setAptLocation(String aptLocation) {
        this.aptLocation.set(aptLocation);
    }

    public void setAptContact(String aptContact) {
        this.aptContact.set(aptContact);
    }

    public StringProperty getAptEndProperty() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime ldt = LocalDateTime.parse(this.aptEnd.getValue(), df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid);
        StringProperty date = new StringProperty(utcDate.toLocalDateTime().toString());
        return date;
    }

    public StringProperty getAptStartProperty() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime ldt = LocalDateTime.parse(this.aptStart.getValue(), df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid);
        StringProperty date = new StringProperty(utcDate.toLocalDateTime().toString());
        return date;
    }

    public StringProperty getAptTitleProperty() {
        return this.aptTitle;
    }

    public StringProperty getAptDescriptionProperty() {
        return this.aptDescription;
    }

    public StringProperty getAptLocationProperty() {
        return this.aptLocation;
    }

    public StringProperty getAptContactProperty() {
        return this.aptContact;
    }

    public LocalDate getDateOnly() {
        Timestamp ts = Timestamp.valueOf(this.aptStart.get());
        ZoneId zid;
        if (this.aptLocation.get().equals("New York")) {
            zid = ZoneId.of("America/New_York");
        } else if (this.aptLocation.get().equals("Phoenix")) {
            zid = ZoneId.of("America/Phoenix");
        } else {
            zid = ZoneId.of("Europe/London");
        }

        ZonedDateTime zdt = ts.toLocalDateTime().atZone(zid);
        LocalDate ld = zdt.toLocalDate();
        return ld;
    }

    public String getTimeOnly() {
        Timestamp ts = Timestamp.valueOf(this.aptStart.get());
        ZonedDateTime zdt;
        ZoneId zid;
        LocalTime lt;
        if (this.aptLocation.get().equals("New York")) {
            zid = ZoneId.of("America/New_York");
            zdt = ts.toLocalDateTime().atZone(zid);
            lt = zdt.toLocalTime().minusHours(4L);
        } else if (this.aptLocation.get().equals("Phoenix")) {
            zid = ZoneId.of("America/Phoenix");
            zdt = ts.toLocalDateTime().atZone(zid);
            lt = zdt.toLocalTime().minusHours(7L);
        } else {
            zid = ZoneId.of("Europe/London");
            zdt = ts.toLocalDateTime().atZone(zid);
            lt = zdt.toLocalTime().plusHours(1L);
        }

        int rawH = Integer.parseInt(lt.toString().split(":")[0]);
        if (rawH > 12) {
            rawH -= 12;
        }

        String ampm;
        if (rawH >= 9 && rawH != 12) {
            ampm = "AM";
        } else {
            ampm = "PM";
        }

        String time = rawH + ":00 " + ampm;
        return time;
    }

    public String get15Time() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime ldt = LocalDateTime.parse(this.aptStart.getValue(), df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid);
        DateTimeFormatter tFormatter = DateTimeFormatter.ofPattern("kk:mm");
        LocalTime localTime = LocalTime.parse(utcDate.toString().substring(11, 16), tFormatter);
        return localTime.toString();
    }
}
