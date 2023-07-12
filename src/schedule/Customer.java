/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

// Importing JavaFX's properties to provide features for our class
// such as being observable, having the ability to bind and unbind, etc.
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Katie-BAMF
 */
public class Customer {

    // These are the properties of the class Customer.
    // They are all defined as private for encapsulation purposes and are made observable to allow listeners to be notified of changes
    private final SimpleIntegerProperty customerID = new SimpleIntegerProperty();
    private final SimpleStringProperty customerName = new SimpleStringProperty();
    private final SimpleStringProperty customerAddress = new SimpleStringProperty();
    private final SimpleObjectProperty<FirstLevelDivisions> customerDivision = new SimpleObjectProperty<>();
    private final SimpleStringProperty customerZip = new SimpleStringProperty();
    private final SimpleStringProperty customerPhone = new SimpleStringProperty();

    // Default constructor for the Customer class
//    public Customer(int id, String name, String address, int divisionId, String phone, String postalCode) {
//    }

    // Overloaded constructor for the Customer class which allows the caller to specify the customer's details at object creation
    // Now the constructor uses divisionId instead of a FirstLevelDivisions object
    public Customer(int id, String name, String address, FirstLevelDivisions division, String phone, String postalCode) {
        this.setCustomerID(id);
        this.setCustomerName(name);
        this.setCustomerAddress(address);
        this.setCustomerDivision(division); // pass FirstLevelDivisions object directly
        this.setCustomerPhone(phone);
        this.setCustomerZip(postalCode);
    }

    // Updated setCustomerDivision method
    public void setCustomerDivision(FirstLevelDivisions division) {
        this.customerDivision.set(division);
    }

    // A new method to get a FirstLevelDivisions object by its ID
    private FirstLevelDivisions getDivisionById(int divisionId) throws SQLException {
        ConnectDB connDB = new ConnectDB(); // Create ConnectDB object to interact with the database
        Connection conn = connDB.makeConnection(); // Establish the connection

        // Fetch all divisions
        List<FirstLevelDivisions> allDivisions = connDB.getAllDivisionsByCountryId(divisionId);

        for (FirstLevelDivisions division : allDivisions) { // Loop through all divisions
            if (division.getDivisionId() == divisionId) { // If the ID matches
                connDB.closeConnection(); // Close the connection
                return division; // Return the division
            }
        }

        connDB.closeConnection(); // Close the connection

        return null; // If no matching division is found, return null
    }


    // Getter methods - these methods provide read access to the object's properties
    public int getCustomerID() {
        return this.customerID.get();
    }

    public String getCustomerName() {
        return this.customerName.get();
    }

    public String getCustomerAddress() {
        return this.customerAddress.get();
    }

    public FirstLevelDivisions getCustomerDivision() {
        return this.customerDivision.get();
    }
    public String getCustomerZip() {
        return this.customerZip.get();
    }

    public String getCustomerPhone() {
        return this.customerPhone.get();
    }

    // Setter methods - these methods provide write access to the object's properties
    public void setCustomerID(int id) {
        this.customerID.set(id);
    }

    public void setCustomerName(String name) {
        this.customerName.set(name);
    }

    public void setCustomerAddress(String address) {
        this.customerAddress.set(address);
    }

//    public void setCustomerDivision(FirstLevelDivisions division) {
//        this.customerDivision.set(division);
//    }
    public void setCustomerZip(String zip) {
        this.customerZip.set(zip);
    }

    public void setCustomerPhone(String phone) {
        this.customerPhone.set(phone);
    }

}
