/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 * FXML Controller class
 *
 * @author Katie-BAMF
 */
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

    //connect to the resource bundle to get the local language
    // this broke stuff
 // ResourceBundle lngbndl = ResourceBundle.getBundle("language", Locale.getDefault());
  //   private ResourceBundle rb = ResourceBundle.getBundle("language", Locale.getDefault());

    /*
      private void setLanguage(){
        ResourceBundle rb = ResourceBundle.getBundle("language", Locale.getDefault());
       // appLabel.setText(rb.getString("appLabel"));
        txtUserName.setText(rb.getString("username"));
        txtPassword.setText(rb.getString("password"));
        btnLogIn.setText(rb.getString("login"));
    }
      
      */
    
    private final DateTimeFormatter localDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final ZoneId localTZ = ZoneId.systemDefault();
    private final ZoneId newzid = ZoneId.systemDefault();
    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Locale locale = Locale.getDefault();
   //     rb = ResourceBundle.getBundle("language", locale);
       //  ResourceBundle rb = ResourceBundle.getBundle("language", Locale.getDefault());
       // appLabel.setText(rb.getString("appLabel"));
     //   txtUserName.setText(rb.getString("username"));
   //     txtPassword.setText(rb.getString("password"));
   //     btnLogIn.setText(rb.getString("login"));
     //   setLanguage();
   //   setProperties();
    }    

    @FXML
    private void login(ActionEvent event)throws SQLException, IOException {
        
        String userName = txtUserName.getText();
        String password = txtPassword.getText(); 
        int userID = getUserID(userName);
        
        Parent root;
        Stage stage;
        User user = new User();

        
        if(correctPassword(userID, password))
        {
            user.setUserID(userID);
            user.setUsername(userName);
            
            //calls method to write current user to the log
            loginLog(user.getUsername());

            //calls mainscreen scene after successful login
        /*    root = FXMLLoader.load(getClass().getResource("/schedule/home.fxml"));
            stage = (Stage) btnLogIn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            */
      //  Stage stage;
       // Parent root;
        stage = (Stage) btnLogIn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/schedule/home.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
            
            } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("Incorrect Username or Password");
            alert.setContentText("Enter valid Username and Password");
            Optional<ButtonType> result = alert.showAndWait();
            
        }
        
    }
    
        //gets User ID for current user
    private int getUserID(String userName) throws SQLException {
        int userID = -1;

        //create statement object
        Statement statement = ConnectDB.conn.createStatement();

        //write SQL statement
        String sqlStatement = "SELECT userID FROM user WHERE userName ='" + userName + "'";

        //create resultset object
        ResultSet result = statement.executeQuery(sqlStatement);

        while (result.next()) {
            userID = result.getInt("userId");
        }
        return userID;
    }
    
    private boolean correctPassword(int userID, String password) throws SQLException {

        //create statement object
        Statement statement = ConnectDB.conn.createStatement();

        //write SQL statement
        String sqlStatement = "SELECT password FROM user WHERE userId ='" + userID + "'";;

        //create resultset object
        ResultSet result = statement.executeQuery(sqlStatement);

        while (result.next()) {
            if (result.getString("password").equals(password)) {
                return true;
            }
        }
        return false;
    }
        
        //creates a new log file if one doesnt exist and inserts login information for current user
    public void loginLog(String user) {
        try {
            String fileName = "loginLog";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.append(DateAndTime.getTimeStamp() + " " + user + " " + "\n");
            System.out.println("New login recorded in log file.");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    
    
      /**
     * setProperties method set the login screen language, works for English or French 
     */
    public void setProperties() {
        
        
        //this broke stuff
        /*
        if (Locale.getDefault().getLanguage().equals("en") ||
            Locale.getDefault().getLanguage().equals("fr")) {
            lblUserName.setText(lngbndl.getString("username"));
            lblPassword.setText(lngbndl.getString("password"));
            btnLogIn.setText(lngbndl.getString("login"));
        }
*/
        //btnLogIn.setText(rb.getString("login"));
        //txtUserName.setText(rb.getString("username"));
        //txtPassword.setText(rb.getString("password"));
        
   //     setLanguage();
        
        txtZone.setText(newzid.getId());



    }
    

    
    
    
    
}



/*chat gpt



import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginForm extends Application {

    private static final String BUNDLE_BASE_NAME = "login"; // base name of resource bundles
    private static final String DEFAULT_LOCALE_LANGUAGE = "en"; // default language
    private static final String DEFAULT_LOCALE_COUNTRY = "US"; // default country
    private static final String LABEL_USERNAME = "label.username"; // key for username label in resource bundles
    private static final String LABEL_PASSWORD = "label.password"; // key for password label in resource bundles
    private static final String LABEL_ZONE_ID = "label.zoneId"; // key for ZoneId label in resource bundles
    private static final String BUTTON_LOGIN = "button.login"; // key for login button in resource bundles
    private static final String ERROR_INVALID_CREDENTIALS = "error.invalidCredentials"; // key for invalid credentials error message in resource bundles

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Determine the user's current language setting
        Locale currentLocale = Locale.getDefault();

        // Load the appropriate resource bundle based on the user's language setting
        ResourceBundle resources = ResourceBundle.getBundle(BUNDLE_BASE_NAME, currentLocale, new UTF8Control());

        // Create a grid pane to hold the form elements
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Create a text element to display the title of the form
        Text title = new Text(resources.getString(LABEL_USERNAME));
        title.setId("title");
        grid.add(title, 0, 0, 2, 1);

        // Create a label and text field for the username
        Label usernameLabel = new Label(resources.getString(LABEL_USERNAME));
        grid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 1);

        // Create a label and password field


Then, in the code, you can use the ResourceBundle to retrieve the appropriate text for each UI element based on the user's language setting by using the keys to look up the values in the ResourceBundle. For example:

Copy code
Label usernameLabel = new Label(resources.getString(LABEL_USERNAME));
Button loginButton = new Button(resources.getString(BUTTON_LOGIN));



import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginForm extends Application {

    private static final String BUNDLE_BASE_NAME = "login"; // base name of resource bundles
    private static final String DEFAULT_LOCALE_LANGUAGE = "en"; // default language
    private static final String DEFAULT_LOCALE_COUNTRY = "US"; // default country
    private static final String LABEL_USERNAME = "label.username"; // key for username label in resource bundles
    private static final String LABEL_PASSWORD = "label.password"; // key for password label in resource bundles
    private static final String LABEL_ZONE_ID = "label.zoneId"; // key for ZoneId label in resource bundles
    private static final String BUTTON_LOGIN = "button.login"; // key for login button in resource bundles
    private static final String ERROR_INVALID_CREDENTIALS = "error.invalidCredentials"; // key for invalid credentials error message in resource bundles

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Determine the user's current language setting
        Locale currentLocale = Locale.getDefault();

        // Load





*/