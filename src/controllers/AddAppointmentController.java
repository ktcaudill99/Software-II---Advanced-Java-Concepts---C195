package controllers;

import constructors.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.ConnectDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class for the Add Appointment screen.
 * <p>
 * This class is responsible for handling the logic of adding a new appointment to the database. It provides various validations
 * including checking if the appointment is within business hours, if it is scheduled for a weekend, and if the appointment
 * overlaps with any existing appointments for the same customer.
 * </p>
 */
public class AddAppointmentController implements Initializable {

    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField locationField;
    @FXML
    private ComboBox<String> contactBox;
    @FXML
    private TextField typeField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private ComboBox<String> startTimeBox, endTimeBox;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField userIdField;
    @FXML
    private Text actionStatus;
    @FXML
    private ComboBox<String> customerBox;
    @FXML
    private TextField appointmentIdField;

    // Add the currentUserTimeZone as a class variable
    private String currentUserTimeZone = ZoneId.systemDefault().getId();


    /**
     * Initializes the controller class by loading contacts, appointment times, and customers.
     * It also sets the User ID field with the ID of the current logged-in user and disables it.
     *
     * @param url            the location used to resolve relative paths for the root object
     * @param resourceBundle the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadContacts();
        loadAppointmentTimes();
        loadCustomers();
        // Disable the User ID field
        userIdField.setDisable(true);

        // Set the User ID field with the ID of the current logged in user
        userIdField.setText(String.valueOf(ConnectDB.getCurrentUserId()));
    }

    /**
     * Loads the contacts from the database and populates the contactBox.
     */
    private void loadContacts() {
        try {
            List<String> contacts = ConnectDB.getAllContacts();
            contactBox.setItems(FXCollections.observableArrayList(contacts));

        } catch (SQLException ex) {
            System.err.println("Error while loading contacts: " + ex.getMessage());
        }
    }

    /**
     * Loads the customers from the database and populates the customerBox.
     */
    private void loadCustomers() {
        try {
            List<String> customers = ConnectDB.getAllCustomers();
            customerBox.setItems(FXCollections.observableArrayList(customers));
        } catch (SQLException ex) {
            System.err.println("Error while loading customers: " + ex.getMessage());
        }
    }

    /**
     * Loads the appointment times and populates the startTimeBox and endTimeBox.
     */
    private void loadAppointmentTimes() {
        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();
        LocalTime firstAppointment = LocalTime.MIN.plusHours(8);
        LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45);
        while (firstAppointment.isBefore(lastAppointment)) {
            appointmentTimes.add(String.valueOf(firstAppointment));
            firstAppointment = firstAppointment.plusMinutes(15);
        }
        startTimeBox.setItems(appointmentTimes);
        endTimeBox.setItems(appointmentTimes);
    }

    /**
     * Handles the save button action.
     * This method collects the appointment details from the form, validates the data, and saves the appointment to the database.
     * It also navigates back to the home screen upon successful saving.
     *
     * @param event the action event triggered by the Save button
     * @throws IOException if there's an issue navigating to the home view
     */
    @FXML
    public void handleSaveButtonAction(ActionEvent event) throws IOException {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String contactName = contactBox.getValue();
        String type = typeField.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String startTime = startTimeBox.getValue();
        String endTime = endTimeBox.getValue();
        String userIdStr = userIdField.getText();
        String customerName = customerBox.getValue();
        LocalDateTime now = LocalDateTime.now();

        // Check if any fields are empty
        if (title.isEmpty() || description.isEmpty() || location.isEmpty() ||
                contactName == null || contactName.isEmpty() || type.isEmpty() ||
                startDate == null || endDate == null ||
                userIdStr.isEmpty()) {
            actionStatus.setText("Please fill all the fields.");
            return;
        }

//        String startDateTimeStr = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " " + startTime + ":00";
//        String endDateTimeStr = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " " + endTime + ":00";
//        LocalDateTime startDateTime = ConnectDB.convertTimeDateUTC(startDateTimeStr);
//        LocalDateTime endDateTime = ConnectDB.convertTimeDateUTC(endDateTimeStr);

        //this for enter time in local time  zone
//        ZoneId userZone = ZoneId.of(currentUserTimeZone);
//        ZonedDateTime startZDT = ZonedDateTime.of(startDate, LocalTime.parse(startTime), userZone);
//        ZonedDateTime endZDT = ZonedDateTime.of(endDate, LocalTime.parse(endTime), userZone);

        // Assume the user enters the time in Eastern Standard Time (EST)
        ZoneId estZone = ZoneId.of("America/New_York");
        ZonedDateTime startZDT = ZonedDateTime.of(startDate, LocalTime.parse(startTime), estZone);
        ZonedDateTime endZDT = ZonedDateTime.of(endDate, LocalTime.parse(endTime), estZone);


//        // Convert the local time to ET for business hour vafndleSaveButtonlidation
//        ZonedDateTime startDateTimeET = startZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
//        ZonedDateTime endDateTimeET = endZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
//
//        if (startDateTimeET.toLocalTime().isBefore(LocalTime.of(8, 0)) || endDateTimeET.toLocalTime().isAfter(LocalTime.of(22, 0))) {
//            actionStatus.setText("Appointment times must be within business hours (8:00 a.m. to 10:00 p.m. ET).");
//            return;
//        }

        // Check if the appointment is within business hours in EST
        if (startZDT.toLocalTime().isBefore(LocalTime.of(8, 0)) || endZDT.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            actionStatus.setText("Appointment times must be within business hours (8:00 a.m. to 10:00 p.m. ET).");
            return;
        }

        // Convert to UTC for storage
        LocalDateTime startDateTimeUTC = startZDT.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime endDateTimeUTC = endZDT.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();


        // Check if the end time is before the start time
        if (endDateTimeUTC.isBefore(startDateTimeUTC)) {
            actionStatus.setText("End time cannot be before start time.");
            return;
        }

        int contactId;
        try {
            contactId = ConnectDB.getContactIdByContactName(contactName);
            if (contactId == -1) {
                actionStatus.setText("Invalid contact name: " + contactName);
                return;
            }
        } catch (SQLException ex) {
            actionStatus.setText("Error while getting contact ID: " + ex.getMessage());
            return;
        }
        int customerId;
        try {
            customerId = ConnectDB.getCustomerIdByCustomerName(customerName);
            if (customerId == -1) {
                actionStatus.setText("Invalid customer name: " + customerName);
                return;
            }
        } catch (SQLException ex) {
            actionStatus.setText("Error while getting customer ID: " + ex.getMessage());
            return;
        }
        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException ex) {
            actionStatus.setText("Customer ID and User ID should be valid numbers.");
            return;
        }
        String currentUser;
        try {
            currentUser = ConnectDB.getUserNameById(userId);
            if (currentUser == null) {
                actionStatus.setText("Invalid user ID: " + userId);
                return;
            }
        } catch (SQLException ex) {
            actionStatus.setText("Error while getting user name: " + ex.getMessage());
            return;
        }

        // Check if the appointment is within business hours
//        ZonedDateTime startDateTimeET = startDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
//        ZonedDateTime endDateTimeET = endDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
//        if (startDateTimeET.toLocalTime().isBefore(LocalTime.of(8, 0)) || endDateTimeET.toLocalTime().isAfter(LocalTime.of(22, 0))) {
//            actionStatus.setText("Appointment times must be within business hours (8:00 a.m. to 10:00 p.m. ET).");
//            return;
//        }
        // Check if the appointment is within business hours
//        if (startDateTimeUTC.toLocalTime().isBefore(LocalTime.of(8, 0)) || endDateTimeUTC.toLocalTime().isAfter(LocalTime.of(22, 0))) {
//            actionStatus.setText("Appointment times must be within business hours (8:00 a.m. to 10:00 p.m. ET).");
//            return;
//        }


        // Check if the appointment is scheduled for a weekend
//        if (startDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || startDateTime.getDayOfWeek() == DayOfWeek.SUNDAY ||
//                endDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || endDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
//            actionStatus.setText("Appointments cannot be scheduled on weekends.");
//            return;
//        }

        // Check if the appointment overlaps with any existing appointments for the same customer
        try {
            if (ConnectDB.doesAppointmentOverlap(customerId, startDateTimeUTC, endDateTimeUTC)) {
                actionStatus.setText("Cannot main overlapping appointments for the same customer.");
                return;
            }
        } catch (SQLException ex) {
            actionStatus.setText("Error while checking for overlapping appointments: " + ex.getMessage());
            return;
        }
        // Check if the appointment is within business hours
//        if (startDateTime.toLocalTime().isBefore(LocalTime.of(8, 0)) || endDateTime.toLocalTime().isAfter(LocalTime.of(22, 0))) {
//            actionStatus.setText("Appointment times must be within business hours (8:00 a.m. to 10:00 p.m. ET).");
//            return;
//        }

        // Create a new Appointment object with the data
        Appointment newAppointment = new Appointment(0, contactId, now, currentUser, customerId,
                description, endDateTimeUTC, now, currentUser, location, startDateTimeUTC, title, type, userId);

        try {
            ConnectDB.saveAppointment(newAppointment);
            Parent homeParent = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
            Scene homeScene = new Scene(homeParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        } catch (SQLException ex) {
            System.err.println("Error while saving appointment: " + ex.getMessage());
        }
    }

    /**
     * Handles the cancel button action.
     * This method displays a confirmation dialog to the user and navigates back to the home screen if the user confirms the cancellation.
     * A lambda expression is used to handle the alert dialog button click event, providing a concise way to define the functionality.
     *
     * @param event the action event triggered by the Cancel button
     * @throws IOException if there's an issue loading the home view
     */
    @FXML
    public void cancelCreation(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to cancel creating this appointment?",
                ButtonType.YES, ButtonType.NO);

        // Using lambda expression to handle the user's response to the alert dialog
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    Parent homeParent = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
                    Scene homeScene = new Scene(homeParent);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(homeScene);
                    window.show();
                } catch (IOException ex) {
                    System.err.println("Error while cancelling appointment creation: " + ex.getMessage());
                }
            }
        });
    }
}
