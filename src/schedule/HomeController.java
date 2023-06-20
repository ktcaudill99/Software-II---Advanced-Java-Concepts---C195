/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.util.MissingResourceException;


/**
 * FXML Controller class
 *
 * @author Katie-BAMF
 */
public class HomeController implements Initializable {

    @FXML
    private TableView<?> tvAppointments;
    @FXML
    private TableColumn<?, ?> colAppID;
    @FXML
    private TableColumn<?, ?> colTitle;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colLocation;
    @FXML
    private TableColumn<?, ?> colContact;
    @FXML
    private TableColumn<?, ?> colType;
    @FXML
    private TableColumn<?, ?> colStart;
    @FXML
    private TableColumn<?, ?> colEnd;
    @FXML
    private TableColumn<?, ?> colCustomerID;
    @FXML
    private TableColumn<?, ?> colUserID;
    @FXML
    private Button btnAppAdd;
    @FXML
    private Button btnAppMod;
    @FXML
    private Button btnAppDel;
    @FXML
    private TableColumn<?, ?> customerID;
    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private TableColumn<?, ?> phoneNumber;
    @FXML
    private TableColumn<?, ?> address;
    @FXML
    private TableColumn<?, ?> state;
    @FXML
    private TableColumn<?, ?> postal;
    @FXML
    private Button btnCustomerAdd;
    @FXML
    private Button btnCustomerMod;
    @FXML
    private Button btnCustomerDel;
    @FXML
    private Button report;
    @FXML
    private Button logout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addAppAction(ActionEvent event) {
    }

    @FXML
    private void modifyAppAction(ActionEvent event) {
    }

    @FXML
    private void deleteAppAction(ActionEvent event) {
    }

    @FXML
    private void addCustomerAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) btnCustomerAdd.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifyCustomerAction(ActionEvent event) {
    }

    @FXML
    private void deleteCustomerAction(ActionEvent event) {
    }
    
}
