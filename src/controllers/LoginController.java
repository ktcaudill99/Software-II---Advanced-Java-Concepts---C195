
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


/**
 * Controller class for managing the login form of the application.
 * This class contains methods for handling user login and logging user activities.
 *
 */
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

    /**
     * Retrieves the default locale of the system running the application.
     *
     * @return The default locale.
     */
    public static Locale getCurrentLocale() {
        return Locale.getDefault();
    }

    /**
     * Sets the labels for the login form based on the current locale.
     *
     * @param rb The resource bundle containing localized strings.
     */
    public void setLoginLabels(ResourceBundle rb) {
        Locale locale =  getCurrentLocale();
        rb = ResourceBundle.getBundle("main/language", Locale.getDefault());
        lblTitle.setText(rb.getString("Login"));
        lblUserName.setText(rb.getString("username"));
        lblPassword.setText(rb.getString("password"));
        btnLogIn.setText(rb.getString("button.Login"));
        lblZone.setText(rb.getString("label.zoneId"));
    }

    /**
     * Initializes the login controller. This method is called after the fxml file has been loaded.
     *
     * @param url The location to resolve relative paths for the root object, or null if unknown.
     * @param rb  The resources used to localize the root object, or null if the root object was not localized.
     */
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

    /**
     * Sets the system's timezone label on the login form.
     */
    private void setZoneLabel() {
        ZoneId zoneId = ZoneId.systemDefault();
        lblZone.setText(zoneId.getId());
    }

    /**
     * Handles login operations including user authentication and scene transition.
     *
     * @param event The event triggered by the login button.
     * @throws SQLException If a database access error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void login(ActionEvent event)throws SQLException, IOException {

        // Get user input from login form
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        // Retrieve user ID from database
        int userID = -1;
        try {
            userID = getUserID(userName);
        } catch (SQLException e) {
            // Handle the runtime error that happens for invalid user ID
            System.err.println("Error fetching user ID: " + e.getMessage());
            // Log the failed login attempt since there's an exception (considered as a failed attempt)
            loginLog(userName, false);
            return;
        }

        // Initialize parent and stage for scene transition
        Parent root;
        Stage stage;
        User user = new User();

        // Check if the user exists in the database and if the password matches
        if(userID != -1 && correctPassword(userID, password)) {
            // Set the user details
            user.setUserID(userID);
            user.setUsername(userName);

            // Log the successful login operation
            loginLog(user.getUsername(), true);

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
            // Get the current locale and resource bundle
            Locale locale = getCurrentLocale();
            ResourceBundle rb = ResourceBundle.getBundle("main/language", locale);

            // Show an alert when the username or password is incorrect
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("alert.title"));
            alert.setHeaderText(rb.getString("alert.header.incorrectCredentials"));
            alert.setContentText(rb.getString("alert.content.validCredentials"));
            Optional<ButtonType> result = alert.showAndWait();

            // Log the failed login attempt
            loginLog(userName, false);
        }
    }

    /**
     * Retrieves the user ID from the database for a given username.
     *
     * @param userName The username to search for.
     * @return The user ID, or -1 if the user was not found.
     * @throws SQLException If a database access error occurs.
     */
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

    /**
     * Verifies the correctness of the password for a given user ID.
     *
     * @param userID The user ID to verify the password for.
     * @param password The password to verify.
     * @return true if the password is correct, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
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

    /**
     * Logs the successful login of a user.
     *
     * @param user The username of the user who attempted to log in.
     * @param isSuccess Indicates if the login attempt was successful or not.
     */
    public void loginLog(String user, boolean isSuccess) {
        try {
            // Specify the file name where the logs are to be written
            String fileName = "login_activity.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

            // Append the current time stamp, the user who tried to log in, and the status
            String status = isSuccess ? "Successful login" : "Failed login";
            writer.append(DateAndTime.getTimeStamp() + " - User: " + user + " - Status: " + status + "\n");

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
