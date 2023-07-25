/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

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
    private ComboBox<FirstLevelDivisions> divisionBox;

    @FXML
    private ComboBox<Country> countryBox;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCountries();
        countryBox.setOnAction(this::loadDivisions);
    }

    private void loadCountries() {
        try {
            List<Country> countries = ConnectDB.getAllCountries();
            countryBox.setItems(FXCollections.observableArrayList(countries));
        } catch (SQLException ex) {
            System.err.println("Error while loading countries: " + ex.getMessage());
        }
    }

    private void loadDivisions(ActionEvent event) {
        Country selectedCountry = countryBox.getSelectionModel().getSelectedItem();
        if (selectedCountry != null) {
            try {
                List<FirstLevelDivisions> divisions = ConnectDB.getAllDivisionsByCountryId(selectedCountry.getCountryId());
                divisionBox.setItems(FXCollections.observableArrayList(divisions));
            } catch (SQLException ex) {
                System.err.println("Error while loading divisions: " + ex.getMessage());
            }
        }
    }

    @FXML
    public void saveCustomer(ActionEvent event) throws IOException {
        String name = nameField.getText();
        String address = addressField.getText();
        String postalCode = postalCodeField.getText();
        String phone = phoneField.getText();
        FirstLevelDivisions division = divisionBox.getSelectionModel().getSelectedItem();
        Country country = countryBox.getSelectionModel().getSelectedItem();
        // Debugging: check if division is null
        if (division == null) {
            System.out.println("No division selected");
            return;
        }

        // Create a new Customer object with the data
        Customer newCustomer = new Customer(0, name, address, division, phone, postalCode);

        // Here, we just pass the data to a database service
        // In real life, you would want to do some data validation before this
        try {
            ConnectDB.saveCustomer(newCustomer);
            // Now we navigate to the home screen
            Parent homeParent = FXMLLoader.load(getClass().getResource("/schedule/home.fxml"));
            Scene homeScene = new Scene(homeParent);
            // This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        } catch (SQLException ex) {
            System.err.println("Error while saving customer: " + ex.getMessage());
        }
    }


    @FXML
    public void cancelCreation(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to cancel creating customer?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Parent homeParent = FXMLLoader.load(getClass().getResource("/schedule/home.fxml"));
            Scene homeScene = new Scene(homeParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        }
    }
}
