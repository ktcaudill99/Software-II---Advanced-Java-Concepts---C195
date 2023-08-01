/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

//import inventoryfx.FXMLController;
import java.io.IOException;
import java.sql.SQLException;

import controllers.LoginController;
import javafx.application.Application;
import static javafx.application.Application.launch;

        import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
        import javafx.stage.Stage;

import java.sql.Connection;

/**
 *
 * @author Katie-BAMF
 */

        public class MainRun extends Application {
    
    // Set main window as FXML.fxml
public static Connection conn;
    
    /**
     *
     * @param stage
     * @throws IOException
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
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, Exception {
       conn = ConnectDB.makeConnection();
        launch(args);
        ConnectDB.closeConnection();
    }
 
    
}

    
