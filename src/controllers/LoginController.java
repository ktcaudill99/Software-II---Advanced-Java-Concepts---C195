
// Purpose: Controller for the login form. Handles login operations and logs the login of a user.
package controllers;

// Necessary import statements for file, database, GUI, and other operations
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;

import constructors.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import main.ConnectDB;
import main.DateAndTime;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.Optional;



// Class that serves as controller for login operations
public class LoginController implements Initializable {

    // FXML fields for interacting with the GUI
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogIn;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblUserName;
    @FXML
    private Label lblZone;

    @FXML
    private Label zoneLabel;

    // Gets the default locale of the system running the application
    public static Locale getCurrentLocale() {
        return Locale.getDefault();
    }

    // Set labels for the login form
    public void setLoginLabels(ResourceBundle rb) {
        Locale locale =  getCurrentLocale();
        rb = ResourceBundle.getBundle("main/language", Locale.getDefault());
        lblTitle.setText(rb.getString("Login"));
        lblUserName.setText(rb.getString("username"));
        lblPassword.setText(rb.getString("password"));
        btnLogIn.setText(rb.getString("button.Login"));
        lblZone.setText(rb.getString("label.zoneId"));
    }
    // Initial setup method when the class is instantiated
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Gets the default locale of the system running the application
        try {
            Locale locale = getCurrentLocale();
            rb = ResourceBundle.getBundle("main/language", locale);
            setLoginLabels(rb);
            setZoneLabel(); // Sets the system's timezone label

        } catch(MissingResourceException e) {
            System.out.println("Resource file missing: " + e);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // Set the system's timezone label
    private void setZoneLabel() {
        ZoneId zoneId = ZoneId.systemDefault();
        lblZone.setText(zoneId.getId());
    }

    // Method to handle login operations
    @FXML
    private void login(ActionEvent event)throws SQLException, IOException {

        // Get user input from login form
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        // Retrieve user ID from database
        int userID = getUserID(userName);

        // Initialize parent and stage for scene transition
        Parent root;
        Stage stage;
        User user = new User();

        // Check if the user exists in the database and if the password matches
        if(userID != -1 && correctPassword(userID, password)) {
            // Set the user details
            user.setUserID(userID);
            user.setUsername(userName);

            // Log the login operation
            loginLog(user.getUsername());

            // Change the scene to home.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            root = loader.load();

            // Get the HomeController instance and call the checkUpcomingAppointments method
            HomeController homeController = loader.getController();
            homeController.checkUpcomingAppointments();

            stage = (Stage) btnLogIn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            // Show an alert when the username or password is incorrect
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("Incorrect Username or Password");
            alert.setContentText("Enter valid Username and Password");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    // Method to retrieve user ID from the database
    private int getUserID(String userName) throws SQLException {
        int userID = -1;

        // Create SQL statement to communicate with the database
        Statement statement = ConnectDB.conn.createStatement();

        // SQL query to fetch user ID
        String sqlStatement = "SELECT User_ID FROM client_schedule.users WHERE User_Name ='" + userName + "'";

        // Execute query and get results
        ResultSet result = statement.executeQuery(sqlStatement);

        // Iterate over the results
        while (result.next()) {
            // Store the user ID
            userID = result.getInt("User_ID");
        }
        // Return the user ID
        return userID;
    }

    // Method to verify the correctness of the password for a given user ID
    private boolean correctPassword(int userID, String password) throws SQLException {
        // Create an SQL statement to communicate with the database
        Statement statement = ConnectDB.conn.createStatement();

        // SQL query to fetch password for the given user ID
        String sqlStatement = "SELECT Password FROM client_schedule.users WHERE User_ID =" + userID;

        // Execute the query and get the result
        ResultSet result = statement.executeQuery(sqlStatement);

        // Iterate over the results
        while (result.next()) {
            // If the password in the database matches the provided password
            if (result.getString("Password").equals(password)) {
                // Return true
                return true;
            }
        }
        // If the password does not match or user does not exist, return false
        return false;
    }

    // Method to log the successful login of a user
    public void loginLog(String user) {
        try {
            // Specify the file name where the logs are to be written
            String fileName = "loginLog";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

            // Append the current time stamp and the user who logged in
            writer.append(DateAndTime.getTimeStamp() + " " + user + " " + "\n");

            // Print a message on the console indicating that a new login has been recorded
            System.out.println("New login recorded in log file.");

            // Close the writer to release resources and ensure that the log is written to the file
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // Print any exceptions that may occur
            System.out.println(e);
        }
    }

}
