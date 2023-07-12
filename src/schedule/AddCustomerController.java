/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Katie-BAMF
 */
public class AddCustomerController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<String> divisionBox;

    @FXML
    private ComboBox<String> countryBox;

    private ConnectDB ConnectDB = new ConnectDB();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCountries();
    }
    private void loadCountries() {
        try {
            List<Country> countries = ConnectDB.getAllCountries();

            for (Country country : countries) {
                countryBox.getItems().add(country.getCountry());
            }
        } catch (SQLException ex) {
            System.err.println("Error while loading countries: " + ex.getMessage());
        }
    }


    @FXML
    public void saveCustomer(ActionEvent event) {
        String name = nameField.getText();
        String address = addressField.getText();
        String postalCode = postalCodeField.getText();
        String phone = phoneField.getText();
        String division = divisionBox.getSelectionModel().getSelectedItem();
        String country = countryBox.getSelectionModel().getSelectedItem();

        // Create a new Customer object with the data
        Customer newCustomer = new Customer(0, name, address, division, phone, postalCode);

        // Here, we just pass the data to a database service
        // In real life, you would want to do some data validation before this
        try {
            ConnectDB.saveCustomer(newCustomer);
        } catch (SQLException ex) {
            System.err.println("Error while saving appointment: " + ex.getMessage());
        }

    }
}