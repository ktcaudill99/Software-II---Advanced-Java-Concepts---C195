
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import constructors.Appointment;
import constructors.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.ConnectDB;

/**
 * Controller class for the Modify Appointment view.
 * This class handles the modification of existing appointments, including loading appointment data
 * and handling user interactions to update or cancel the modification.
 *
 *
 */
public class ModifyAppointmentController implements Initializable {

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
    private Appointment appointmentToModify;

    /**
     * Sets the appointment to modify and loads its data into the form.
     *
     * @param appointment The appointment to modify.
     */
    public void setAppointment(Appointment appointment) {
        this.appointmentToModify = appointment;
        loadAppointmentData();
    }

    private String currentUserTimeZone = ZoneId.systemDefault().getId();


    /**
     * Loads the appointment data into the form fields.
     */
    private void loadAppointmentData() {
        if (appointmentToModify != null) {
            titleField.setText(appointmentToModify.getAppointmentTitle());
            descriptionField.setText(appointmentToModify.getAppointmentDescription());
            locationField.setText(appointmentToModify.getAppointmentLocation());
            typeField.setText(appointmentToModify.getAppointmentType());

            // Convert UTC LocalDateTime to user's time zone
//            ZonedDateTime startZDT = appointmentToModify.getStart().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of(currentUserTimeZone));
//            ZonedDateTime endZDT = appointmentToModify.getEnd().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of(currentUserTimeZone));


//            startDatePicker.setValue(startZDT.toLocalDate());
//            startTimeBox.setValue(startZDT.toLocalTime().toString());
//            endDatePicker.setValue(endZDT.toLocalDate());
//            endTimeBox.setValue(endZDT.toLocalTime().toString());

            // Convert UTC LocalDateTime to Eastern Standard Time (EST)
            ZonedDateTime startZDT = appointmentToModify.getStart().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime endZDT = appointmentToModify.getEnd().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("America/New_York"));

            startDatePicker.setValue(startZDT.toLocalDate());
            startTimeBox.setValue(startZDT.toLocalTime().toString());
            endDatePicker.setValue(endZDT.toLocalDate());
            endTimeBox.setValue(endZDT.toLocalTime().toString());

//            startDatePicker.setValue(startZDT.toLocalDate());
//            startTimeBox.setValue(startZDT.toLocalTime().toString());
//            endDatePicker.setValue(endZDT.toLocalDate());
//            endTimeBox.setValue(endZDT.toLocalTime().toString());

            try {
                // Get the names of the contact and customer for this appointment
                String contactName = ConnectDB.getContactNameById(appointmentToModify.getContactID());
                String customerName = ConnectDB.getCustomerNameById(appointmentToModify.getCustomerID());
                String countryName = ConnectDB.getCountryByAppointmentId(appointmentToModify.getAppointmentID());

                contactBox.setValue(contactName);
                customerBox.setValue(customerName);

            } catch (SQLException ex) {
                System.err.println("Error while loading appointment data: " + ex.getMessage());
            }

            // Set the User ID and Appointment ID fields
            userIdField.setText(String.valueOf(appointmentToModify.getUserID()));
            appointmentIdField.setText(String.valueOf(appointmentToModify.getAppointmentID()));
        }
    }

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     *
     * @param url The location to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadContacts();
        loadAppointmentTimes();
        loadCustomers();
       // loadCountries();  // New method to load countries
        //preloadRegionForSelectedCountry();  // New method to preload regions for the selected country
        loadAppointmentData();


        // Disable the User ID field
        userIdField.setDisable(true);

        // Set the User ID field with the ID of the current logged in user
        userIdField.setText(String.valueOf(ConnectDB.getCurrentUserId()));

        javafx.util.StringConverter<LocalDate> converter = new javafx.util.StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                try {
                    return LocalDate.parse(string);
                } catch (DateTimeParseException e) {
                    actionStatus.setText("Error: Please enter a valid date.");
                    return null;
                }
            }
        };

        startDatePicker.setConverter(converter);
        endDatePicker.setConverter(converter);
    }

    /**
     * Loads all contacts into the contactBox ComboBox.
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
     * Loads all customers into the customerBox ComboBox.
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
     * Loads all appointment times into the startTimeBox and endTimeBox ComboBoxes.
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
     * Handles the save button action for updating the appointment.
     * This method is marked as deprecated, consider updating or replacing it.
     *
     * @param event The event triggered by the save button.
     * @throws IOException If an I/O error occurs.
     * @throws SQLException If a database access error occurs.
     */
    @Deprecated
    public void updateAppointment(ActionEvent event) throws IOException, SQLException {
        String title = titleField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        String description = descriptionField.getText();
        String contactName = contactBox.getValue();
        String customerName = customerBox.getValue();

        //for entering in local time zone
//        ZoneId userZone = ZoneId.of(currentUserTimeZone);
//        ZonedDateTime startZDT = ZonedDateTime.of(startDatePicker.getValue(), LocalTime.parse(startTimeBox.getValue()), userZone);
//        ZonedDateTime endZDT = ZonedDateTime.of(endDatePicker.getValue(), LocalTime.parse(endTimeBox.getValue()), userZone);

//        LocalDateTime startDateTimeUTC = startZDT.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
//        LocalDateTime endDateTimeUTC = endZDT.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

        // Assume the user enters the time in Eastern Standard Time (EST)
        ZoneId estZone = ZoneId.of("America/New_York");
        ZonedDateTime startZDT = ZonedDateTime.of(startDatePicker.getValue(), LocalTime.parse(startTimeBox.getValue()), estZone);
        ZonedDateTime endZDT = ZonedDateTime.of(endDatePicker.getValue(), LocalTime.parse(endTimeBox.getValue()), estZone);

        // Convert the EST time to UTC for storage in the database
        LocalDateTime startDateTimeUTC = startZDT.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime endDateTimeUTC = endZDT.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

        int userId = Integer.parseInt(userIdField.getText());
        int appointmentId = Integer.parseInt(appointmentIdField.getText());
        int contactId = ConnectDB.getContactIdByContactName(contactName);
        int customerId = ConnectDB.getCustomerIdByCustomerName(customerName);

        // Check if the end time is before the start time
        if (endDateTimeUTC.isBefore(startDateTimeUTC)) {
            actionStatus.setText("End time cannot be before start time.");
            return;
        }

        // Check if the appointment overlaps with any existing appointments for the same customer
        try {
            if (ConnectDB.doesAppointmentOverlap(customerId, startDateTimeUTC, endDateTimeUTC)) {
                actionStatus.setText("Cannot maintain overlapping appointments for the same customer.");
                return;
            }
        } catch (SQLException ex) {
            actionStatus.setText("Error while checking for overlapping appointments: " + ex.getMessage());
            return;
        }

        // Check if the appointment is within business hours (converted to ET for consistency)
        ZonedDateTime startDateTimeET = startZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime endDateTimeET = endZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        if (startDateTimeET.toLocalTime().isBefore(LocalTime.of(8, 0)) || endDateTimeET.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            actionStatus.setText("Appointment times must be within business hours (8:00 a.m. to 10:00 p.m. ET).");
            return;
        }

        // Create the updated Appointment object
        Appointment updatedAppointment = new Appointment(
                appointmentId,
                contactId,
                startDateTimeUTC,
                "", // createdBy
                customerId,
                description,
                endDateTimeUTC,
                startDateTimeUTC, // lastUpdate
                "", // lastUpdatedBy
                location,
                startDateTimeUTC,
                title,
                type,
                userId
        );

        try {
            ConnectDB.updateAppointment(updatedAppointment);
            Parent homeParent = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
            Scene homeScene = new Scene(homeParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        } catch (SQLException ex) {
            System.err.println("Error while updating appointment: " + ex.getMessage());
        }
    }


    /**
     * Handles the cancel button action for canceling the appointment update.
     * This method is marked as deprecated, consider updating or replacing it.
     *
     * @param event The event triggered by the cancel button.
     * @throws IOException If an I/O error occurs.
     */
    @Deprecated
    public void cancelUpdate(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to cancel updating appointment?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Parent homeParent = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
            Scene homeScene = new Scene(homeParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        }
    }
}
