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


package schedule;

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

public class LoginController implements Initializable {

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

    private final DateTimeFormatter localDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final ZoneId localTZ = ZoneId.systemDefault();
    private final ZoneId newzid = ZoneId.systemDefault();
    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Locale locale = Locale.getDefault();
    }

    @FXML
    private void login(ActionEvent event)throws SQLException, IOException {

        String userIdInput = txtUserName.getText();
        String password = txtPassword.getText();
        int userID;
        try {
            userID = Integer.parseInt(userIdInput);
        } catch (NumberFormatException e) {
            return;
        }

        Parent root;
        Stage stage;
        User user = new User();

        if(correctPassword(userID, password)) {
            user.setUserID(userID);
            user.setUsername(getUsername(userID));

            loginLog(user.getUsername());

            stage = (Stage) btnLogIn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/schedule/home.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("Incorrect User ID or Password");
            alert.setContentText("Enter valid User ID and Password");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    private String getUsername(int userID) throws SQLException {
        String userName = "";

        Statement statement = ConnectDB.conn.createStatement();

        String sqlStatement = "SELECT User_Name FROM client_schedule.users WHERE User_ID =" + userID;

        ResultSet result = statement.executeQuery(sqlStatement);

        while (result.next()) {
            userName = result.getString("User_Name");
        }
        return userName;
    }

    private boolean correctPassword(int userID, String password) throws SQLException {
        Statement statement = ConnectDB.conn.createStatement();

        String sqlStatement = "SELECT Password FROM client_schedule.users WHERE User_ID =" + userID;

        ResultSet result = statement.executeQuery(sqlStatement);

        while (result.next()) {
            if (result.getString("Password").equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void loginLog(String user) {
        try {
            String fileName = "loginLog";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            new BufferedWriter(new FileWriter(fileName, true));
            writer.append(DateAndTime.getTimeStamp() + " " + user + " " + "\n");
            System.out.println("New login recorded in log file.");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void setProperties() {
        txtZone.setText(newzid.getId());
    }
}
