package schedule;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import javafx.event.ActionEvent;

public class ReportController {
    @FXML
    private TextArea reportArea;

    @FXML
    private void generateReports() {
        reportArea.clear();
        generateReport1();
        generateReport2();
    }

    private void generateReport1() {
        String query = "SELECT COUNT(*), Type, MONTH(Start) as Month FROM client_schedule.appointments GROUP BY Type, Month ORDER BY Month, Type";

        try {
            Statement statement = ConnectDB.conn.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                int count = results.getInt(1);
                String type = results.getString(2);
                int month = results.getInt(3);

                reportArea.appendText("Month: " + month + ", Type: " + type + ", Count: " + count + "\n");
            }
        } catch (SQLException e) {
            reportArea.appendText("Error generating report: " + e.getMessage() + "\n");
        }
    }

    private void generateReport2() {
        String query = "SELECT Contact_ID FROM client_schedule.contacts";
        try {
            Statement statement = ConnectDB.conn.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                int contactId = results.getInt(1);
                generateContactSchedule(contactId);
            }
        } catch (SQLException e) {
            reportArea.appendText("Error generating report: " + e.getMessage() + "\n");
        }
    }

    private void generateContactSchedule(int contactId) {
        String query = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM client_schedule.appointments WHERE Contact_ID = ? ORDER BY Start";

        try {
            PreparedStatement statement = ConnectDB.conn.prepareStatement(query);
            statement.setInt(1, contactId);
            ResultSet results = statement.executeQuery();
            reportArea.appendText("Schedule for Contact " + contactId + ":\n");
            while (results.next()) {
                int appointmentId = results.getInt(1);
                String title = results.getString(2);
                String type = results.getString(3);
                String description = results.getString(4);
                Timestamp start = results.getTimestamp(5);
                Timestamp end = results.getTimestamp(6);
                int customerId = results.getInt(7);

                reportArea.appendText("Appointment ID: " + appointmentId + ", Title: " + title + ", Type: " + type + ", Description: " + description + ", Start: " + start + ", End: " + end + ", Customer ID: " + customerId + "\n");
            }
        } catch (SQLException e) {
            reportArea.appendText("Error generating schedule: " + e.getMessage() + "\n");
        }
    }

    @FXML
    private void backToHome(ActionEvent event) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene homeScene = new Scene(homeParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(homeScene);
        window.show();
    }
}
