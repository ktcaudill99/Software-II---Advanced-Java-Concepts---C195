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

    private ConnectDB ConnectDB = new ConnectDB();

    private Customer customerToModify;  // Add this field at the class level

    public void setCustomer(Customer customer) {
        this.customerToModify = customer;
        loadCustomerData();
    }


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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCountries();
        loadAllDivisions();
        countryBox.setOnAction(this::loadDivisions);
        // Assuming you have a method to fetch and load the customer data
        loadCustomerData();
    }

    // Add a method to load the customer data here

    private void loadCountries() {
        try {
            List<Country> countries = ConnectDB.getAllCountries();
            countryBox.setItems(FXCollections.observableArrayList(countries));
        } catch (SQLException ex) {
            System.err.println("Error while loading countries: " + ex.getMessage());
        }
    }

    private void loadAllDivisions() {
        try {
            List<FirstLevelDivisions> divisions = ConnectDB.getAllDivisions();
            divisionBox.setItems(FXCollections.observableArrayList(divisions));
        } catch (SQLException ex) {
            System.err.println("Error while loading divisions: " + ex.getMessage());
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
            Parent homeParent = FXMLLoader.load(getClass().getResource("/schedule/home.fxml"));
            Scene homeScene = new Scene(homeParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        } catch (SQLException ex) {
            System.err.println("Error while updating customer: " + ex.getMessage());
        }
    }

    @FXML
    public void cancelUpdate(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to cancel updating customer?", ButtonType.YES, ButtonType.NO);
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
