
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
 * Controller class for the Add Customer view.
 * This class is responsible for handling user interactions for creating a new customer.
 * It provides methods to load countries and divisions, save the new customer to the database,
 * and cancel the customer creation.
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
    private ComboBox<FirstLevelDivisions> divisionBox;
    @FXML
    private ComboBox<Country> countryBox;

    /**
     * Initializes the controller class by loading all countries into the countryBox.
     * Sets the countryBox's onAction event to load divisions based on the selected country.
     *
     * @param url            the location used to resolve relative paths for the root object
     * @param rb             the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCountries();
        countryBox.setOnAction(this::loadDivisions);
    }

    /**
     * Retrieves all countries from the database and loads them into the countryBox ComboBox.
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
     * Event handler for loading divisions.
     * Retrieves all divisions associated with the selected country from the database
     * and loads them into the divisionBox ComboBox.
     *
     * @param event the action event triggered by the countryBox ComboBox
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
     * Event handler for the Save button.
     * Retrieves customer details from the form, validates the input, and saves the new customer to the database.
     * Navigates back to the home view upon successful saving.
     *
     * @param event the action event triggered by the Save button
     * @throws IOException if there's an issue navigating to the home view
     */
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

        try {
            ConnectDB.saveCustomer(newCustomer);
            Parent homeParent = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
            Scene homeScene = new Scene(homeParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        } catch (SQLException ex) {
            System.err.println("Error while saving customer: " + ex.getMessage());
        }
    }

    /**
     * Event handler for the Cancel button.
     * Displays a confirmation dialog to the user and navigates back to the home screen if the user confirms the cancellation.
     *  Using lambda expression to handle the user's response to the alert dialog
     *
     * @param event the action event triggered by the Cancel button
     * @throws IOException if there's an issue loading the home view
     */
    @FXML
    public void cancelCreation(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to cancel creating this customer?",
                ButtonType.YES, ButtonType.NO);

        // Using lambda expression to handle the user's response to the alert dialog
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    Parent homeParent = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
                    Scene homeScene = new Scene(homeParent);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(homeScene);
                    window.show();
                } catch (IOException ex) {
                    System.err.println("Error while cancelling appointment creation: " + ex.getMessage());
                }
            }
        });
    }}
