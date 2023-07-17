package schedule;

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
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddAppointmentController  implements Initializable {

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

    // Assuming Appointment object
    private Appointment selectedAppointment;

    private AddAppointmentController ConnectDB;
    // Assuming ConnectDB class has a static Connection object
    //private Connection conn = ConnectDB.conn;
    //private Connection conn;
    private ConnectDB Conn = new ConnectDB();
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get the connection
     //   this.conn = ConnectDB.makeConnection();

        // Populate ComboBox with contact names
        populateContacts();

        // If we are updating, populate the fields with the selected appointment details
        if (selectedAppointment != null) {
            populateFields(selectedAppointment);
        }
    }

    private void populateContacts() {
        // Sample code. Replace with actual contact fetching logic
        contactBox.getItems().addAll("Contact 1", "Contact 2", "Contact 3");
    }

    private void saveAppointment(Appointment appointment) {
        // Call the method to save the appointment in the database.
        // This method should be implemented in the ConnectDB class.
        ConnectDB.saveAppointment(appointment);
        actionStatus.setText("Appointment " + appointment.getAppointmentID() + " of type " + appointment.getAppointmentType() + " saved.");
    }


    @FXML
    private void handleSaveButtonAction() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String contactName = contactBox.getValue();
        String type = typeField.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String customerId = customerIdField.getText();
        String userId = userIdField.getText();

        // Data validation. You might want to add more checks here.
        if (title.isEmpty() || description.isEmpty() || location.isEmpty() ||
                contactName.isEmpty() || type.isEmpty() || startDate == null ||
                endDate == null || customerId.isEmpty() || userId.isEmpty()) {

            actionStatus.setText("Please fill all fields.");
            return;
        }

        // Create new appointment and set the attributes
        Appointment appointment = new Appointment(Integer.parseInt(customerId), description,
                Timestamp.valueOf(endDate.atStartOfDay()).toLocalDateTime(), location,
                Timestamp.valueOf(startDate.atStartOfDay()).toLocalDateTime(), title,
                type, Integer.parseInt(userId));
        appointment.setAppointmentTitle(title);
        appointment.setAppointmentDescription(description);
        appointment.setAppointmentLocation(location);
        // Assume setContactName() sets the contact name
        appointment.setContactName(contactName);
        appointment.setAppointmentType(type);
        appointment.setStart(Timestamp.valueOf(startDate.atStartOfDay()).toLocalDateTime());
        appointment.setEnd(Timestamp.valueOf(endDate.atStartOfDay()).toLocalDateTime());
        appointment.setCustomerID(Integer.parseInt(customerId));
        appointment.setUserID(Integer.parseInt(userId));

        saveAppointment(appointment);
    }


    private void populateFields(Appointment appointment) {
//        ContactDB contactDB;
//        try {
//            contactDB = new ContactDB();
//            Contact contact = contactDB.getContact(appointment.getContactID());
//            if (contact != null) {
//                contactBox.setValue(contact.getContactName());
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        // Fill the form fields with appointment data
        titleField.setText(appointment.getAppointmentTitle());
        descriptionField.setText(appointment.getAppointmentDescription());
        locationField.setText(appointment.getAppointmentLocation());
        // Assume getContactName() returns contact name
        contactBox.setValue(appointment.getContactName());
        typeField.setText(appointment.getAppointmentType());
        startDatePicker.setValue(appointment.getStart().toLocalDate());
        endDatePicker.setValue(appointment.getEnd().toLocalDate());
        customerIdField.setText(Integer.toString(appointment.getCustomerID()));
        userIdField.setText(Integer.toString(appointment.getUserID()));
    }
    @FXML
    public void cancelCreation(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel creating this appointment?", ButtonType.YES, ButtonType.NO);
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
