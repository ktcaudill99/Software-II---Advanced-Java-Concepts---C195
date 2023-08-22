
package constructors;
// These are the necessary imports for the date/time functionality in this class
import java.time.LocalDateTime;

/**
 *  Constructor for the Appointment
 *  This class represents an Appointment object with all the necessary fields
 *
 */
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

    /**
     * Creates an appointment with specific details.
     * @param appointmentID the ID of the appointment
     * @param contactID the ID of the contact
     * @param createDate the date the appointment was created
     * @param createdBy the individual who created the appointment
     * @param customerID the ID of the customer
     * @param appointmentDescription the description of the appointment
     * @param end the end date and time of the appointment
     * @param lastUpdate the date and time of the last update
     * @param lastUpdatedBy the individual who last updated the appointment
     * @param appointmentLocation the location of the appointment
     * @param start the start date and time of the appointment
     * @param appointmentTitle the title of the appointment
     * @param appointmentType the type of appointment
     * @param userID the ID of the user
     */
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

    /**
     * Simplified constructor for creating an appointment with fewer details.
     * @param customerID the ID of the customer
     * @param appointmentDescription the description of the appointment
     * @param end the end date and time of the appointment
     * @param appointmentLocation the location of the appointment
     * @param start the start date and time of the appointment
     * @param appointmentTitle the title of the appointment
     * @param appointmentType the type of appointment
     * @param userID the ID of the user
     */
        public Appointment(int customerID, String appointmentDescription, LocalDateTime end,
                           String appointmentLocation, LocalDateTime start, String appointmentTitle,
                           String appointmentType, int userID) {
            this(0, 0, LocalDateTime.now(), "", customerID, appointmentDescription, end, LocalDateTime.now(),
                    "", appointmentLocation, start, appointmentTitle, appointmentType, userID);
        }

    /**
     * Simplified constructor for creating an appointment with the most basic details.
     * @param appointmentID the ID of the appointment
     * @param appointmentTitle the title of the appointment
     * @param appointmentDescription the description of the appointment
     * @param appointmentLocation the location of the appointment
     */
        public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation) {
            this.appointmentID = appointmentID;
            this.appointmentTitle = appointmentTitle;
            this.appointmentDescription = appointmentDescription;
            this.appointmentLocation = appointmentLocation;
        }

    // Below are the getter and setter methods for all instance variables

    /**
     * @return the appointment ID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * @return the appointment title
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    /**
     * @return the appointment description
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /**
     * @return the appointment location
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    /**
     * @return the appointment type
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * @return the start date and time of the appointment
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets the start date and time of the appointment.
     * @param start the start date and time to set
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * @return the end date and time of the appointment
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets the end date and time of the appointment.
     * @param end the end date and time to set
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * @return the customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @return the user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @return the contact ID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * @return the name of the individual who created the appointment
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @return the name of the individual who last updated the appointment
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    // Getter for createDate
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    // Setter for createDate
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    // Getter for lastUpdate
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    // Setter for lastUpdate
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}