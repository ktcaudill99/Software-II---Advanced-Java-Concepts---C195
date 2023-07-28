/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.converter.StringConverter;
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

    private Appointment appointmentToModify;  // Add this field at the class level

    public void setAppointment(Appointment appointment) {
        this.appointmentToModify = appointment;
        loadAppointmentData();
    }

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadContacts();
        loadAppointmentTimes();
        loadCustomers();
        // Assuming you have a method to fetch and load the appointment data
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


    private void loadContacts() {
        try {
            List<String> contacts = ConnectDB.getAllContacts();
            contactBox.setItems(FXCollections.observableArrayList(contacts));
        } catch (SQLException ex) {
            System.err.println("Error while loading contacts: " + ex.getMessage());
        }
    }

    private void loadCustomers() {
        try {
            List<String> customers = ConnectDB.getAllCustomers();
            customerBox.setItems(FXCollections.observableArrayList(customers));
        } catch (SQLException ex) {
            System.err.println("Error while loading customers: " + ex.getMessage());
        }
    }

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
                "", // createdBy - replace with the correct value
                customerId,
                description,
                endDateTime,
                startDateTime, // lastUpdate - replace with the correct value
                "", // lastUpdatedBy - replace with the correct value
                location,
                startDateTime,
                title,
                type,
                userId
        );
        try {
            ConnectDB.updateAppointment(updatedAppointment);
            Parent homeParent = FXMLLoader.load(getClass().getResource("/schedule/home.fxml"));
            Scene homeScene = new Scene(homeParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        } catch (SQLException ex) {
            System.err.println("Error while updating appointment: " + ex.getMessage());
        }
    }

    @Deprecated
    public void cancelUpdate(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to cancel updating appointment?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Parent homeParent = FXMLLoader.load(getClass().getResource("/schedule/home.fxml"));
            Scene homeScene = new Scene(homeParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        }
    }
}
