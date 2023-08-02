
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import constructors.Country;
import constructors.Customer;
import constructors.FirstLevelDivisions;
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
import main.ConnectDB;

/**
 * Controller class for the Modify Customer view.
 * This class is responsible for modifying existing customer information, loading country and division data,
 * and handling user interactions to update or cancel the modification.
 *
 * @author Katherine Caudill
 */
public class ModifyCustomerController implements Initializable {

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

    private main.ConnectDB ConnectDB = new ConnectDB();

    private Customer customerToModify;  // Add this field at the class level


    /**
     * Sets the customer to modify and loads its data into the form.
     *
     * @param customer The customer to modify.
     */
    public void setCustomer(Customer customer) {
        this.customerToModify = customer;
        loadCustomerData();
    }

    /**
     * Loads the customer data into the form fields.
     */
    private void loadCustomerData() {
        if (customerToModify != null) {
            nameField.setText(customerToModify.getCustomerName());
            addressField.setText(customerToModify.getCustomerAddress());
            postalCodeField.setText(customerToModify.getCustomerZip());
            phoneField.setText(customerToModify.getCustomerPhone());

            // Get the countryId from the selected division
            int countryId = customerToModify.getCustomerDivision().getCountryId();

            // Fetch the Country object using the countryId
            Country country = null;
            for (Country c : countryBox.getItems()) {
                if (c.getCountryId() == countryId) {
                    country = c;
                    break;
                }
            }

            // Select the fetched Country in the ComboBox
            countryBox.getSelectionModel().select(country);

            // Select the division after the country has been selected
            divisionBox.getSelectionModel().select(customerToModify.getCustomerDivision());
        }
    }

    /**
     * Initializes the controller class. Automatically called after the FXML file has been loaded.
     *
     * @param url The location to resolve relative paths for the root object, or null if unknown.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCountries();
        loadAllDivisions();
        countryBox.setOnAction(this::loadDivisions);
        loadCustomerData();
    }

    /**
     * Loads all countries into the countryBox ComboBox.
     */
    private void loadCountries() {
        try {
            List<Country> countries = ConnectDB.getAllCountries();
            countryBox.setItems(FXCollections.observableArrayList(countries));
        } catch (SQLException ex) {
            System.err.println("Error while loading countries: " + ex.getMessage());
        }
    }

    /**
     * Loads all divisions into the divisionBox ComboBox.
     */
    private void loadAllDivisions() {
        try {
            List<FirstLevelDivisions> divisions = ConnectDB.getAllDivisions();
            divisionBox.setItems(FXCollections.observableArrayList(divisions));
        } catch (SQLException ex) {
            System.err.println("Error while loading divisions: " + ex.getMessage());
        }
    }

    /**
     * Loads divisions into the divisionBox ComboBox based on the selected country.
     *
     * @param event The event triggered by the country selection.
     */
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

    /**
     * Handles the update customer button action. Adds the updated customer to the database.
     *
     * @param event The event triggered by the update button.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    public void updateCustomer(ActionEvent event) throws IOException {
        String name = nameField.getText();
        String address = addressField.getText();
        String postalCode = postalCodeField.getText();
        String phone = phoneField.getText();
        FirstLevelDivisions division = divisionBox.getSelectionModel().getSelectedItem();
        Country country = countryBox.getSelectionModel().getSelectedItem();
        if (division == null) {
            System.out.println("No division selected");
            return;
        }

        Customer updatedCustomer = new Customer(customerToModify.getCustomerID(), name, address, division, phone, postalCode);

        try {
            ConnectDB.updateCustomer(updatedCustomer);
            Parent homeParent = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
            Scene homeScene = new Scene(homeParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        } catch (SQLException ex) {
            System.err.println("Error while updating customer: " + ex.getMessage());
        }
    }

    /**
     * Handles the cancel button action. Cancels updating the customer and returns to the home screen.
     *
     * @param event The event triggered by the cancel button.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    public void cancelUpdate(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to cancel updating customer?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Parent homeParent = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
            Scene homeScene = new Scene(homeParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        }
    }
}
