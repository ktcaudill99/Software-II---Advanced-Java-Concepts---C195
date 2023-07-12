/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

// Importing JavaFX's properties to provide features for our class
// such as being observable, having the ability to bind and unbind, etc.
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

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
    private final SimpleIntegerProperty customerDivision = new SimpleIntegerProperty();    private final SimpleStringProperty customerZip = new SimpleStringProperty();
    private final SimpleStringProperty customerPhone = new SimpleStringProperty();

    // Default constructor for the Customer class
    public Customer() {
    }

    // Overloaded constructor for the Customer class which allows the caller to specify the customer's details at object creation
    public Customer(int id, String name, String address, int division, String phone, String zip) {        this.setCustomerID(id);
        this.setCustomerName(name);
        this.setCustomerAddress(address);
        this.setCustomerDivision(division);
        this.setCustomerPhone(phone);
        this.setCustomerZip(zip);
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

    public int getCustomerDivision() {
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

    public void setCustomerDivision(int division) {
        this.customerDivision.set(division);
    }

    public void setCustomerZip(String zip) {
        this.customerZip.set(zip);
    }

    public void setCustomerPhone(String phone) {
        this.customerPhone.set(phone);
    }

}
