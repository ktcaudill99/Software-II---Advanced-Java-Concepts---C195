
package schedule;

// These are the necessary imports for the date/time functionality in this class
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;


// This class represents an Appointment object with all the necessary fields


    // This is the constructor for the Appointment class
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

        public Appointment(int appointmentID, int contactID, LocalDateTime createDate, String createdBy,
                           int customerID, String appointmentDescription, LocalDateTime end, LocalDateTime lastUpdate,
                           String lastUpdatedBy, String appointmentLocation, LocalDateTime start, String appointmentTitle,
                           String appointmentType, int userID) {
            this.appointmentID = appointmentID;
            this.contactID = contactID;
            this.createDate = createDate;
            this.createdBy = createdBy;
            this.customerID = customerID;
            this.appointmentDescription = appointmentDescription;
            this.end = end;
            this.lastUpdate = lastUpdate;
            this.lastUpdatedBy = lastUpdatedBy;
            this.appointmentLocation = appointmentLocation;
            this.start = start;
            this.appointmentTitle = appointmentTitle;
            this.appointmentType = appointmentType;
            this.userID = userID;
        }
        public Appointment(int customerID, String appointmentDescription, LocalDateTime end,
                           String appointmentLocation, LocalDateTime start, String appointmentTitle,
                           String appointmentType, int userID) {
            this(0, 0, LocalDateTime.now(), "", customerID, appointmentDescription, end, LocalDateTime.now(),
                    "", appointmentLocation, start, appointmentTitle, appointmentType, userID);
        }

        public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation) {
            this.appointmentID = appointmentID;
            this.appointmentTitle = appointmentTitle;
            this.appointmentDescription = appointmentDescription;
            this.appointmentLocation = appointmentLocation;
        }

        // Below are the getter methods for all instance variables
        public int getAppointmentID() {
        return appointmentID;
    }

        public String getAppointmentTitle() {
            return appointmentTitle;
        }

        public void setAppointmentTitle(String appointmentTitle) {
            this.appointmentTitle = appointmentTitle;
        }

        public String getAppointmentDescription() {
            return appointmentDescription;
        }

        public void setAppointmentDescription(String appointmentDescription) {
            this.appointmentDescription = appointmentDescription;
        }

        public String getAppointmentLocation() {
            return appointmentLocation;
        }

        public void setAppointmentLocation(String appointmentLocation) {
            this.appointmentLocation = appointmentLocation;
        }

        public String getAppointmentType() {
            return appointmentType;
        }

        public void setAppointmentType(String appointmentType) {
            this.appointmentType = appointmentType;
        }

        public LocalDateTime getStart() {
            return start;
        }

        public void setStart(LocalDateTime start) {
            this.start = start;
        }

        public LocalDateTime getEnd() {
            return end;
        }

        public void setEnd(LocalDateTime end) {
            this.end = end;
        }

        public int getCustomerID() {
            return customerID;
        }

        public void setCustomerID(int customerID) {
            this.customerID = customerID;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public int getContactID() {
            return contactID;
        }

        public void setContactID(int contactID) {
            this.contactID = contactID;
        }

        public LocalDateTime getCreateDate() {
            return createDate;
        }

        public void setCreateDate(LocalDateTime createDate) {
            this.createDate = createDate;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public LocalDateTime getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(LocalDateTime lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public String getLastUpdatedBy() {
            return lastUpdatedBy;
        }

        public void setLastUpdatedBy(String lastUpdatedBy) {
            this.lastUpdatedBy = lastUpdatedBy;
        }
    }