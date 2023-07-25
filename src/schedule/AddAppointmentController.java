package schedule;

import javafx.collections.FXCollections;
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
    private DatePicker endDatePicker;

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField userIdField;

    @FXML
    private Text actionStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadContacts();
    }

    private void loadContacts() {
        try {
            List<String> contacts = ConnectDB.getAllContacts();
            contactBox.setItems(FXCollections.observableArrayList(contacts));
        } catch (SQLException ex) {
            System.err.println("Error while loading contacts: " + ex.getMessage());
        }
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
        String customerIdStr = customerIdField.getText();
        String userIdStr = userIdField.getText();

        if (title.isEmpty() || description.isEmpty() || location.isEmpty() ||
                contactName == null || contactName.isEmpty() || type.isEmpty() ||
                startDate == null || endDate == null || customerIdStr.isEmpty() ||
                userIdStr.isEmpty()) {
            actionStatus.setText("Please fill all the fields.");
            return;
        }

        int contactId;
        try {
            contactId = ConnectDB.getContactIdByName(contactName);
        } catch (SQLException ex) {
            actionStatus.setText("Error while getting contact ID: " + ex.getMessage());
            return;
        }

        int customerId, userId;
        try {
            customerId = Integer.parseInt(customerIdStr);
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException ex) {
            actionStatus.setText("Customer ID and User ID should be valid numbers.");
            return;
        }

        // Create a new Appointment object with the data
        Appointment newAppointment = new Appointment(customerId, description,
                Timestamp.valueOf(endDate.atStartOfDay()).toLocalDateTime(), location,
                Timestamp.valueOf(startDate.atStartOfDay()).toLocalDateTime(), title,
                type, userId);
        newAppointment.setContactId(contactId);
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
