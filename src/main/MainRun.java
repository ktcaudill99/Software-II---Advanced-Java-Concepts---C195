
package main;

import java.io.IOException;
import java.sql.SQLException;

import controllers.LoginController;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

/**
 * The {@code MainRun} class serves as the main entry point for the application.
 * It initializes the connection to the database and launches the JavaFX application,
 * starting with the login screen.
 * <p>
 * This class extends the {@code Application} class from the JavaFX framework.
 *
 */

public class MainRun extends Application {

    /**
     * The connection object used for interacting with the database.
     */
    public static Connection conn;

    /**
     * Starts the JavaFX application by loading the login screen.
     *
     * @param stage The primary stage for this application, onto which
     *              the application scene can be set.
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
          try {
           
         FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("../views/login.fxml"));

        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        
        Scene scene = new Scene(root);
        LoginController mainScreen = fxmlLoader.getController();
        stage.setScene(scene);
        stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method of the application.
     * <p>
     * It establishes a connection to the database, launches the JavaFX application,
     * and then closes the database connection after the application has exited.
     *
     * @param args the command line arguments.
     * @throws SQLException If a database access error occurs.
     * @throws Exception If an error occurs during the initialization process.
     */
    public static void main(String[] args) throws SQLException, Exception {
       conn = ConnectDB.makeConnection();
        launch(args);
        ConnectDB.closeConnection();
    }
 
    
}

    
