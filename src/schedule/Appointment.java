
package schedule;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;


public class Appointment {
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerID;
    private int userID;
    private int contactID;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;


    public Appointment(int appointmentID, int contactId, LocalDateTime createDate, String createdBy,
                       int customerId, String appointmentDescription, LocalDateTime end, LocalDateTime lastUpdate,
                       String lastUpdatedBy, String appointmentLocation, LocalDateTime start, String appointmentTitle,
                       String appointmentType, int userId) {
        this.appointmentID = appointmentID;
        this.contactID = contactId;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.customerID = customerId;
        this.appointmentDescription = appointmentDescription;
        this.end = end;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.appointmentLocation = appointmentLocation;
        this.start = start;
        this.appointmentTitle = appointmentTitle;
        this.appointmentType = appointmentType;
        this.userID = userId;
    }

    public int getAppointmentID() {
            return appointmentID;
        }

        public String getAppointmentTitle() {
            return appointmentTitle;
        }

        public String getAppointmentDescription() {
            return appointmentDescription;
        }

        public String getAppointmentLocation() {
            return appointmentLocation;
        }

        public String getAppointmentType() {
            return appointmentType;
        }

        public LocalDateTime getStart() {
            return start;
        }

        public LocalDateTime getEnd() {
            return end;
        }

        public int getCustomerID() {
            return customerID;
        }

        public int getUserID() {
            return userID;
        }

        public int getContactID() {
            return contactID;
        }

        public LocalDateTime getCreateDate() {
            return createDate;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public LocalDateTime getLastUpdate() {
            return lastUpdate;
        }

        public String getLastUpdatedBy() {
            return lastUpdatedBy;
        }
    }
