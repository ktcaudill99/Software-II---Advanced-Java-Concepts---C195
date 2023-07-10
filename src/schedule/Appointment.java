/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import java.time.LocalDateTime;

public class Appointment {
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private LocalDateTime start;
    //private String start;
    private LocalDateTime end;
    public int customerID;
    public int userID;
    public int contactID;

    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription,
                       String appointmentLocation, String appointmentType) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     *
     * @return appointmentID
     */
    public int getAppointmentID() {

        return appointmentID;
    }

    /**
     *
     * @return appointmentTitle
     */
    public String getAppointmentTitle() {

        return appointmentTitle;
    }

    /**
     *
     * @return appointmentDescription
     */
    public String getAppointmentDescription() {

        return appointmentDescription;
    }

    /**
     *
     * @return appointmentLocation
     */
    public String getAppointmentLocation() {

        return appointmentLocation;
    }

    /**
     *
     * @return appointmentType
     */
    public String getAppointmentType() {

        return appointmentType;
    }


    /**
     *
     * @return start
     */
    public LocalDateTime getStart() {
        System.out.println("start " + start);

        return start;
    }



    /**
     *
     * @return end
     */
    public LocalDateTime getEnd() {

        return end;
    }

    /**
     *
     * @return customerID
     */
    public int getCustomerID () {

        return customerID;
    }

    /**
     *
     * @return userID
     */
    public int getUserID() {

        return userID;
    }

    /**
     *
     * @return contactID
     */
    public int getContactID() {

        return contactID;
    }

}

