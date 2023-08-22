
package constructors;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import main.ConnectDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Represents a customer with details such as name, address, division, phone, and postal code.
 */
public class Customer {

    // These are the properties of the class Customer.
    private final SimpleIntegerProperty customerID = new SimpleIntegerProperty();
    private final SimpleStringProperty customerName = new SimpleStringProperty();
    private final SimpleStringProperty customerAddress = new SimpleStringProperty();
    private final SimpleObjectProperty<FirstLevelDivisions> customerDivision = new SimpleObjectProperty<>();
    private final SimpleStringProperty customerZip = new SimpleStringProperty();
    private final SimpleStringProperty customerPhone = new SimpleStringProperty();

    /**
     * Constructs a Customer object with the specified details.
     *
     * @param id         the customer's unique ID
     * @param name       the customer's name
     * @param address    the customer's address
     * @param division   the customer's division
     * @param phone      the customer's phone number
     * @param postalCode the customer's postal code
     */
    public Customer(int id, String name, String address, FirstLevelDivisions division, String phone, String postalCode) {
        this.setCustomerID(id);
        this.setCustomerName(name);
        this.setCustomerAddress(address);
        this.setCustomerDivision(division); // pass FirstLevelDivisions object directly
        this.setCustomerPhone(phone);
        this.setCustomerZip(postalCode);
    }
    /**
     * Sets the customer's division.
     *
     * @param division the customer's division
     */
    public void setCustomerDivision(FirstLevelDivisions division) {
        this.customerDivision.set(division);
    }

    /**
     * Retrieves a FirstLevelDivisions object by its ID.
     *
     * @param divisionId the division ID
     * @return the division matching the provided ID, or null if not found
     * @throws SQLException if an SQL error occurs
     */
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
    public void setCustomerAddress(String address) {this.customerAddress.set(address);}
    public void setCustomerZip(String zip) {
        this.customerZip.set(zip);
    }
    public void setCustomerPhone(String phone) {
        this.customerPhone.set(phone);
    }

}
