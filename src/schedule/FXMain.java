/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import schedule.LoginController;
//import inventoryfx.FXMLController;
import schedule.ConnectDB;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;

/**
 *
 * @author Katie-BAMF
 */
/*
public class FXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        
    }
*/
        public class FXMain extends Application {
    
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
           
         FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("login.fxml"));

        Parent root = FXMLLoader.load(getClass().getResource("/schedule/login.fxml"));
        
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

    
