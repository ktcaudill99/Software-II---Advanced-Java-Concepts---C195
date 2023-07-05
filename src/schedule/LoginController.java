///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package schedule;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import schedule.ConnectDB;
//import schedule.User;
//import schedule.DateAndTime;
//
//import java.net.URL;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.time.format.FormatStyle;
//import java.util.Locale;
//import java.util.Optional;
//import java.util.ResourceBundle;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.fxml.LoadException;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.stage.Stage;
//import java.util.MissingResourceException;
//
///**
// * FXML Controller class
// *
// * @author Katie-BAMF
// */
//public class LoginController implements Initializable {
//
//    @FXML
//    private TextField txtUserName;
//    @FXML
//    private PasswordField txtPassword;
//    @FXML
//    private Button btnLogIn;
//    @FXML
//    private Label title;
//    @FXML
//    private Label lblPassword;
//    @FXML
//    private Label lblUserName;
//    @FXML
//    private Label txtZone;
//
//    //connect to the resource bundle to get the local language
//    // this broke stuff
// // ResourceBundle lngbndl = ResourceBundle.getBundle("language", Locale.getDefault());
//  //   private ResourceBundle rb = ResourceBundle.getBundle("language", Locale.getDefault());
//
//    /*
//      private void setLanguage(){
//        ResourceBundle rb = ResourceBundle.getBundle("language", Locale.getDefault());
//       // appLabel.setText(rb.getString("appLabel"));
//        txtUserName.setText(rb.getString("username"));
//        txtPassword.setText(rb.getString("password"));
//        btnLogIn.setText(rb.getString("login"));
//    }
//
//      */
//
//    private final DateTimeFormatter localDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
//    private final ZoneId localTZ = ZoneId.systemDefault();
//    private final ZoneId newzid = ZoneId.systemDefault();
//    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
//
//
//    /**
//     * Initializes the controller class.
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//        Locale locale = Locale.getDefault();
//   //     rb = ResourceBundle.getBundle("language", locale);
//       //  ResourceBundle rb = ResourceBundle.getBundle("language", Locale.getDefault());
//       // appLabel.setText(rb.getString("appLabel"));
//     //   txtUserName.setText(rb.getString("username"));
//   //     txtPassword.setText(rb.getString("password"));
//   //     btnLogIn.setText(rb.getString("login"));
//     //   setLanguage();
//   //   setProperties();
//    }
//
//    @FXML
//    private void login(ActionEvent event)throws SQLException, IOException {
//
//        String userName = txtUserName.getText();
//        String password = txtPassword.getText();
//        int userID = getUserID(userName);
//
//        Parent root;
//        Stage stage;
//        User user = new User();
//
//
//        if(correctPassword(userID, password))
//        {
//            user.setUserID(userID);
//            user.setUsername(userName);
//
//            //calls method to write current user to the log
//            loginLog(user.getUsername());
//
//            //calls mainscreen scene after successful login
//        /*    root = FXMLLoader.load(getClass().getResource("/schedule/home.fxml"));
//            stage = (Stage) btnLogIn.getScene().getWindow();
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
//            */
//      //  Stage stage;
//       // Parent root;
//        stage = (Stage) btnLogIn.getScene().getWindow();
//        root = FXMLLoader.load(getClass().getResource("/schedule/home.fxml"));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//            } else {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("");
//            alert.setHeaderText("Incorrect Username or Password");
//            alert.setContentText("Enter valid Username and Password");
//            Optional<ButtonType> result = alert.showAndWait();
//
//        }
//
//    }
//
//        //gets User ID for current user
//    private int getUserID(String userName) throws SQLException {
//        int userID = 0;
//
//        //create statement object
//        Statement statement = ConnectDB.conn.createStatement();
//
//        //write SQL statement
//        String sqlStatement = "SELECT User_ID FROM client_schedule.users WHERE User_ID ='" + userName + "'";
//
//        //create resultset object
//        ResultSet result = statement.executeQuery(sqlStatement);
//
//        //get all rows from result set
//        while (result.next()) {
//            userID = result.getInt("userId");
//        }
//        return userID;
//    }
//
//    private boolean correctPassword(int userID, String password) throws SQLException {
//
//        //create statement object
//        Statement statement = ConnectDB.conn.createStatement();
////
//        //write SQL statement
//        String sqlStatement = "SELECT Password FROM client_schedule.users WHERE User_ID ='" + userID + "'";;
//
//        //create resultset object
//        ResultSet result = statement.executeQuery(sqlStatement);
//
//        while (result.next()) {
//            if (result.getString("password").equals(password)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//        //creates a new log file if one doesnt exist and inserts login information for current user
//    public void loginLog(String user) {
//        try {
//            String fileName = "loginLog";
//            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
//            writer.append(DateAndTime.getTimeStamp() + " " + user + " " + "\n");
//            System.out.println("New login recorded in log file.");
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }
//
//
//
//      /**
//     * setProperties method set the login screen language, works for English or French
//     */
//    public void setProperties() {
//
//
//        //this broke stuff
//        /*
//        if (Locale.getDefault().getLanguage().equals("en") ||
//            Locale.getDefault().getLanguage().equals("fr")) {
//            lblUserName.setText(lngbndl.getString("username"));
//            lblPassword.setText(lngbndl.getString("password"));
//            btnLogIn.setText(lngbndl.getString("login"));
//        }
//*/
//        //btnLogIn.setText(rb.getString("login"));
//        //txtUserName.setText(rb.getString("username"));
//        //txtPassword.setText(rb.getString("password"));
//
//   //     setLanguage();
//
//        txtZone.setText(newzid.getId());
//
//
//
//    }
//
//
//
//
//
//
//}
//

// Import necessary libraries
package schedule;

// Necessary import statements for file, database, GUI, and other operations
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import schedule.ConnectDB;
import schedule.User;
import schedule.DateAndTime;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.MissingResourceException;

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
    private Label title;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblUserName;
    @FXML
    private Label txtZone;

    // Date and time formatter objects
    private final DateTimeFormatter localDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final ZoneId localTZ = ZoneId.systemDefault();
    private final ZoneId newzid = ZoneId.systemDefault();
    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    // Initial setup method when the class is instantiated
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Gets the default locale of the system running the application
        Locale locale = Locale.getDefault();
    }

    // Method to handle login operations
    // When the login button is pressed
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
            stage = (Stage) btnLogIn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/schedule/home.fxml"));
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
        Statement statement =ConnectDB.conn.createStatement();

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

    // Method to set the properties of the scene (like timezone)
    public void setProperties() {
        txtZone.setText(newzid.getId());
    }
}
