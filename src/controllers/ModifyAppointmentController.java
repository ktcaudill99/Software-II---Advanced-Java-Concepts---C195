
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;

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
 * This class is the controller for the Modify Appointment view.
 * It handles the modification of an existing appointment.
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

    public void setAppointment(Appointment appointment) {
        this.appointmentToModify = appointment;
        loadAppointmentData();
    }

    // Load the appointment data into the form
    private void loadAppointmentData() {
        if (appointmentToModify != null) {
            titleField.setText(appointmentToModify.getAppointmentTitle());
            descriptionField.setText(appointmentToModify.getAppointmentDescription());
            locationField.setText(appointmentToModify.getAppointmentLocation());
            typeField.setText(appointmentToModify.getAppointmentType());

            // Convert the LocalDateTime to LocalDate and LocalTime
            LocalDate startDate = appointmentToModify.getStart().toLocalDate();
            LocalTime startTime = appointmentToModify.getStart().toLocalTime();
            LocalDate endDate = appointmentToModify.getEnd().toLocalDate();
            LocalTime endTime = appointmentToModify.getEnd().toLocalTime();

            startDatePicker.setValue(startDate);
            startTimeBox.setValue(startTime.toString());
            endDatePicker.setValue(endDate);
            endTimeBox.setValue(endTime.toString());

            try {
                // Get the names of the contact and customer for this appointment
                String contactName = ConnectDB.getContactNameById(appointmentToModify.getContactID());
                String customerName = ConnectDB.getCustomerNameById(appointmentToModify.getCustomerID());

                // Set the ComboBoxes to the selected contact and customer
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

    // Handle the save button action
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadContacts();
        loadAppointmentTimes();
        loadCustomers();
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

    // Load all contacts into the contactBox
    private void loadContacts() {
        try {
            List<String> contacts = ConnectDB.getAllContacts();
            contactBox.setItems(FXCollections.observableArrayList(contacts));
        } catch (SQLException ex) {
            System.err.println("Error while loading contacts: " + ex.getMessage());
        }
    }

    // Load all customers into the customerBox
    private void loadCustomers() {
        try {
            List<String> customers = ConnectDB.getAllCustomers();
            customerBox.setItems(FXCollections.observableArrayList(customers));
        } catch (SQLException ex) {
            System.err.println("Error while loading customers: " + ex.getMessage());
        }
    }

    // Load all appointment times into the startTimeBox and endTimeBox
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

    // Handle the save button action
    @Deprecated
    public void updateAppointment(ActionEvent event) throws IOException, SQLException {
        String title = titleField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        String description = descriptionField.getText();
        String contactName = contactBox.getValue();
        String customerName = customerBox.getValue();
        LocalDateTime startDateTime = LocalDateTime.of(startDatePicker.getValue(), LocalTime.parse(startTimeBox.getValue()));
        LocalDateTime endDateTime = LocalDateTime.of(endDatePicker.getValue(), LocalTime.parse(endTimeBox.getValue()));
        int userId = Integer.parseInt(userIdField.getText());
        int appointmentId = Integer.parseInt(appointmentIdField.getText());

        int contactId = ConnectDB.getContactIdByContactName(contactName);
        int customerId = ConnectDB.getCustomerIdByCustomerName(customerName);
        Appointment updatedAppointment = new Appointment(
                appointmentId,
                contactId,
                startDateTime,
                "", // createdBy
                customerId,
                description,
                endDateTime,
                startDateTime, // lastUpdate
                "", // lastUpdatedBy
                location,
                startDateTime,
                title,
                type,
                userId
        );

        // Check if the appointment is within business hours
        ZonedDateTime startDateTimeET = startDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime endDateTimeET = endDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
        if (startDateTimeET.toLocalTime().isBefore(LocalTime.of(8, 0)) || endDateTimeET.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            actionStatus.setText("Appointment times must be within business hours (8:00 a.m. to 10:00 p.m. ET).");
            return;
        }


        // Check if the appointment is scheduled for a weekend
        if (startDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || startDateTime.getDayOfWeek() == DayOfWeek.SUNDAY ||
                endDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || endDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            actionStatus.setText("Appointments cannot be scheduled on weekends.");
            return;
        }

        // Check if the end time is before the start time
        if (endDateTime.isBefore(startDateTime)) {
            actionStatus.setText("End time cannot be before start time.");
            return;
        }

        // Check if the appointment overlaps with any existing appointments for the same customer
        try {
            if (ConnectDB.doesAppointmentOverlap(customerId, startDateTime, endDateTime)) {
                actionStatus.setText("Cannot main overlapping appointments for the same customer.");
                return;
            }
        } catch (SQLException ex) {
            actionStatus.setText("Error while checking for overlapping appointments: " + ex.getMessage());
            return;
        }
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

    // Handle the cancel button action
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
