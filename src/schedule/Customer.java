/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Katie-BAMF
 */
public class Customer {

    private final SimpleIntegerProperty customerId = new SimpleIntegerProperty();
    private final SimpleStringProperty customerName = new SimpleStringProperty();
    private final SimpleStringProperty customerAddress = new SimpleStringProperty();
    private final SimpleStringProperty customerCity = new SimpleStringProperty();
    private final SimpleStringProperty customerZip = new SimpleStringProperty();
    private final SimpleStringProperty customerPhone = new SimpleStringProperty();

    public Customer() {
    }

    public Customer(int id, String name, String address, String city, String phone, String zip) {
        this.setCustomerId(id);
        this.setCustomerName(name);
        this.setCustomerAddress(address);
        this.setCustomerCity(city);
        this.setCustomerPhone(phone);
        this.setCustomerZip(zip);
    }

    //getters

    public int getCustomerId() {
        return this.customerId.get();
    }

    public String getCustomerName() {
        return this.customerName.get();
    }

    public String getCustomerAddress() {
        return this.customerAddress.get();
    }

    public String getCustomerCity() {
        return this.customerCity.get();
    }

    public String getCustomerZip() {
        return this.customerZip.get();
    }

    public String getCustomerPhone() {
        return this.customerPhone.get();
    }

    //setters
    public void setCustomerId(int id) {
        this.customerId.set(id);
    }

    public void setCustomerName(String name) {
        this.customerName.set(name);
    }

    public void setCustomerAddress(String address) {
        this.customerAddress.set(address);
    }

    public void setCustomerCity(String city) {
        this.customerCity.set(city);
    }

    public void setCustomerZip(String zip) {
        this.customerZip.set(zip);
    }

    public void setCustomerPhone(String phone) {
        this.customerPhone.set(phone);
    }


}
