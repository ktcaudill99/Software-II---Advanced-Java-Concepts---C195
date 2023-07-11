
package schedule;

// These are the necessary imports for the date/time functionality in this class
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
    }
