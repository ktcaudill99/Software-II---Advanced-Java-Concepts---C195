
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
public class Appointment {
    // These are the instance variables for the Appointment class
    private int appointmentID; // Unique identifier for an appointment
    private String appointmentTitle; // Title of the appointment
    private String appointmentDescription; // Description of the appointment
    private String appointmentLocation; // Location of the appointment
    private String appointmentType; // Type of the appointment
    private LocalDateTime start; // Start time of the appointment
    private LocalDateTime end; // End time of the appointment
    private int customerID; // Unique identifier for the customer
    private int userID; // Unique identifier for the user
    private int contactID; // Unique identifier for the contact
    private LocalDateTime createDate; // Creation date of the appointment
    private String createdBy; // Creator of the appointment
    private LocalDateTime lastUpdate; // Last update date of the appointment
    private String lastUpdatedBy; // Last updater of the appointment

    // This is the constructor for the Appointment class
    public Appointment(int appointmentID, int contactId, LocalDateTime createDate, String createdBy,
                       int customerId, String appointmentDescription, LocalDateTime end, LocalDateTime lastUpdate,
                       String lastUpdatedBy, String appointmentLocation, LocalDateTime start, String appointmentTitle,
                       String appointmentType, int userId) {
        // Initialize instance variables
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

    public Appointment(int customerID, String appointmentDescription, LocalDateTime end,
                       String appointmentLocation, LocalDateTime start, String appointmentTitle,
                       String appointmentType, int userID) {
        this.appointmentID = 0; // default value
        this.contactID = 0; // default value
        this.createDate = LocalDateTime.now(); // default value
        this.createdBy = ""; // default value
        this.customerID = customerID;
        this.appointmentDescription = appointmentDescription;
        this.end = end;
        this.appointmentLocation = appointmentLocation;
        this.start = start;
        this.appointmentTitle = appointmentTitle;
        this.appointmentType = appointmentType;
        this.userID = userID;
        this.lastUpdate = LocalDateTime.now(); // default value
        this.lastUpdatedBy = ""; // default value
    }
    // Below are the getter methods for all instance variables
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

    private String contactName;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setAppointmentTitle(String title) {
        this.appointmentTitle = title;
    }

    public void setAppointmentDescription(String description) {
        this.appointmentDescription = description;
    }


    public void setAppointmentLocation(String location) {
        this.appointmentLocation = location;
    }
    public void setAppointmentType(String type) {
        this.appointmentType = type;
    }
//    public void setStart(Timestamp timestamp) {
//    }
//
//    public void setEnd(Timestamp timestamp) {
//    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public void setCustomerId(int i) {
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setUserId(int i) {
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    }
