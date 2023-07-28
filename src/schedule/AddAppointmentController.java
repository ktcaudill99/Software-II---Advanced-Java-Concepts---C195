package schedule;

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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

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
      //  String currentUser = ConnectDB.getUserNameById(int userId);

        if (title.isEmpty() || description.isEmpty() || location.isEmpty() ||
                contactName == null || contactName.isEmpty() || type.isEmpty() ||
                startDate == null || endDate == null ||
                userIdStr.isEmpty()) {
            actionStatus.setText("Please fill all the fields.");
            return;
        }

        String startDateTimeStr = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " " + startTime + ":00";
        String endDateTimeStr = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " " + endTime + ":00";
        LocalDateTime startDateTime = ConnectDB.convertTimeDateUTC(startDateTimeStr);
        LocalDateTime endDateTime = ConnectDB.convertTimeDateUTC(endDateTimeStr);

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

        // Check if the appointment overlaps with any existing appointments for the same customer
        try {
            if (ConnectDB.doesAppointmentOverlap(customerId, startDateTime, endDateTime)) {
                actionStatus.setText("Cannot schedule overlapping appointments for the same customer.");
                return;
            }
        } catch (SQLException ex) {
            actionStatus.setText("Error while checking for overlapping appointments: " + ex.getMessage());
            return;
        }

        // Check if the appointment is within business hours
        if (startDateTime.toLocalTime().isBefore(LocalTime.of(8, 0)) || endDateTime.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            actionStatus.setText("Appointment times must be within business hours (8:00 a.m. to 10:00 p.m. ET).");
            return;
        }

        // Create a new Appointment object with the data
        Appointment newAppointment = new Appointment(0, contactId, now, currentUser, customerId,
                description, endDateTime, now, currentUser, location, startDateTime, title, type, userId);

        try {
            ConnectDB.saveAppointment(newAppointment);  // pass in contactId as well
            // Now we navigate to the home screen
            Parent homeParent = FXMLLoader.load(getClass().getResource("/schedule/home.fxml"));
            Scene homeScene = new Scene(homeParent);
            // This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        } catch (SQLException ex) {
            System.err.println("Error while saving appointment: " + ex.getMessage());
        }
    }



    @FXML
    public void cancelCreation(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to cancel creating this appointment?",
                ButtonType.YES, ButtonType.NO);
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
