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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private TextField contactIdField;

    @FXML
    private TextField userIdField;

    @FXML
    private Text actionStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadContacts();
        loadAppointmentTimes();
    }


    private void loadContacts() {
        try {
            List<String> contacts = ConnectDB.getAllContacts();
            contactBox.setItems(FXCollections.observableArrayList(contacts));
            contactBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                // When a new contact is selected, get the corresponding customer ID
                if (newValue != null) {
                    try {
                        int contactId = ConnectDB.getContactIdByContactName(newValue);
                        contactIdField.setText(String.valueOf(contactId));
                    } catch (SQLException ex) {
                        System.err.println("Error while getting contact ID: " + ex.getMessage());
                    }
                }
            });
        } catch (SQLException ex) {
            System.err.println("Error while loading contacts: " + ex.getMessage());
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
        String customerIdStr = contactIdField.getText();
        String userIdStr = userIdField.getText();


        if (title.isEmpty() || description.isEmpty() || location.isEmpty() ||
                contactName == null || contactName.isEmpty() || type.isEmpty() ||
                startDate == null || endDate == null || customerIdStr.isEmpty() ||
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


        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException ex) {
            actionStatus.setText("Customer ID and User ID should be valid numbers.");
            return;
        }


        // Create a new Appointment object with the data
        Appointment newAppointment = new Appointment(contactId, description, endDateTime, location, startDateTime, title, type, userId);
       // newAppointment.setContactId(contactId);
        // Here, we just pass the data to a database service
        // In real life, you would want to do some data validation before this
        try {
            ConnectDB.saveAppointment(newAppointment, contactId);  // pass in contactId as well
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
