package main;

import constructors.Appointment;
import constructors.Customer;
import javafx.collections.ObservableList;

/**
 * The {@code ScheduleService} class provides services related to scheduling,
 * such as managing appointments and customers.
 * <p>
 * This class maintains observable lists of {@code Appointment} and {@code Customer} objects,
 * and provides methods to manipulate these lists, such as deleting a customer and their associated appointments.
 *
 * @author Katherine Caudill
 */
public class ScheduleService {
    private ObservableList<Appointment> appointments; // List of all appointments
    private ObservableList<Customer> customers; // List of all customers

    /**
     * Constructs a new {@code ScheduleService} with the given lists of appointments and customers.
     *
     * @param appointments The observable list of appointments.
     * @param customers The observable list of customers.
     */
    public ScheduleService(ObservableList<Appointment> appointments, ObservableList<Customer> customers) {
        this.appointments = appointments;
        this.customers = customers;
    }

    /**
     * Deletes a customer and all their associated appointments from the lists.
     * <p>
     * This method first removes the specified customer from the customers list,
     * then removes all appointments associated with that customer from the appointments list.
     *
     * @param customerID The ID of the customer to be deleted.
     */
    public void deleteCustomerAndAppointments(int customerID) {
        // First remove the customer from the customers list
        customers.removeIf(customer -> customer.getCustomerID() == customerID);

        // Then remove the appointments associated with that customer
        appointments.removeIf(appointment -> appointment.getCustomerID() == customerID);
    }
}