package main;

import constructors.Appointment;
import constructors.Customer;
import javafx.collections.ObservableList;

public class ScheduleService {
    private ObservableList<Appointment> appointments;
    private ObservableList<Customer> customers;

    public ScheduleService(ObservableList<Appointment> appointments, ObservableList<Customer> customers) {
        this.appointments = appointments;
        this.customers = customers;
    }

    public void deleteCustomerAndAppointments(int customerID) {
        // First remove the customer from the customers list
        customers.removeIf(customer -> customer.getCustomerID() == customerID);

        // Then remove the appointments associated with that customer
        appointments.removeIf(appointment -> appointment.getCustomerID() == customerID);
    }
}