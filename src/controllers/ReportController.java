package controllers;

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
import main.ConnectDB;


/**
 * Controller class for the report view.
 * This class is responsible for generating various reports related to appointments, contacts, and scheduling.
 * It provides functionality to generate reports based on appointment types, contact schedules, and contact information.
 *
 *
 */
public class ReportController {
    @FXML
    private TextArea reportArea1;
    @FXML
    private TextArea reportArea2;
    @FXML
    private TextArea contactsReportArea;

    /**
     * Generates the first report, which groups appointments by type and month.
     * The report displays the count, type, and month for each grouping.
     */
    @FXML
    private void generateReport1() {
        reportArea1.clear();
        String query = "SELECT COUNT(*), Type, MONTH(Start) as Month FROM client_schedule.appointments GROUP BY Type, Month ORDER BY Month, Type";
        try {
            Statement statement = ConnectDB.conn.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                int count = results.getInt(1);
                String type = results.getString(2);
                int month = results.getInt(3);
                reportArea1.appendText("Month: " + month + ", Type: " + type + ", Count: " + count + "\n");
            }
        } catch (SQLException e) {
            reportArea1.appendText("Error generating report: " + e.getMessage() + "\n");
        }
    }

    /**
     * Generates the second report, which shows the schedule for each contact.
     * The report includes details about appointments, such as ID, title, type, description, start time, end time, and customer ID.
     */
    @FXML
    private void generateReport2() {
        reportArea2.clear();
        String query = "SELECT Contact_ID FROM client_schedule.contacts";
        try {
            Statement statement = ConnectDB.conn.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                int contactId = results.getInt(1);
                generateContactSchedule(contactId);
            }
        } catch (SQLException e) {
            reportArea2.appendText("Error generating report: " + e.getMessage() + "\n");
        }
    }

    /**
     * Generates the contact schedule for a specific contact.
     * This is a helper method for the {@link #generateReport2()} method.
     *
     * @param contactId The ID of the contact for whom the schedule is generated.
     */
    private void generateContactSchedule(int contactId) {
        String query = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM client_schedule.appointments WHERE Contact_ID = ? ORDER BY Start";
        try {
            PreparedStatement statement = ConnectDB.conn.prepareStatement(query);
            statement.setInt(1, contactId);
            ResultSet results = statement.executeQuery();
            reportArea2.appendText("Schedule for Contact " + contactId + ":\n");
            while (results.next()) {
                int appointmentId = results.getInt(1);
                String title = results.getString(2);
                String type = results.getString(3);
                String description = results.getString(4);
                Timestamp start = results.getTimestamp(5);
                Timestamp end = results.getTimestamp(6);
                int customerId = results.getInt(7);

                reportArea2.appendText("Appointment ID: " + appointmentId + ", Title: " + title + ", Type: " + type + ", Description: " + description + ", Start: " + start + ", End: " + end + ", Customer ID: " + customerId + "\n");
            }
        } catch (SQLException e) {
            reportArea2.appendText("Error generating main: " + e.getMessage() + "\n");
        }
    }

    /**
     * Generates a report for contacts, listing their ID, name, and email.
     */
    @FXML
    private void generateContactsReport() {
        contactsReportArea.clear();
        String query = "SELECT Contact_ID, Contact_Name, Email FROM client_schedule.contacts";
        try {
            Statement statement = ConnectDB.conn.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                int contactId = results.getInt("Contact_ID");
                String contactName = results.getString("Contact_Name");
                String email = results.getString("Email");
                contactsReportArea.appendText("Contact ID: " + contactId + ", Name: " + contactName + ", Email: " + email + "\n");
            }
        } catch (SQLException e) {
            contactsReportArea.appendText("Error generating report: " + e.getMessage() + "\n");
        }
    }

    /**
     * Handles the action to return to the home view.
     * This method is triggered by the "Back to Home" button.
     *
     * @param event The event triggered by the button click.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void backToHome(ActionEvent event) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getResource("../views/home.fxml"));
        Scene homeScene = new Scene(homeParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(homeScene);
        window.show();
    }

}
