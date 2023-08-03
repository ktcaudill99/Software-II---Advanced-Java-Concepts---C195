
package main;

// import java.sql package to establish and manage connections with the database
import constructors.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ConnectDB} class is responsible for handling all database-related operations,
 * including establishing a connection to the database, executing queries, and managing data retrieval
 * and manipulation for various entities like customers, appointments, contacts, countries, and divisions.
 * <p>
 * This class encapsulates the functionality required to interact with the MySQL database, using JDBC.
 * It provides methods to save, update, retrieve, and manipulate data related to the client scheduling system.
 *
 * @author Katherine Caudill
 */
public class ConnectDB {

    // These are the properties required to establish the connection with the database
    private static final String databaseName = "mysql"; // Name of the database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + databaseName
            + "?verifyServerCertificate=false"
            + "&useSSL=true"
            + "&requireSSL=true"; // URL of the database server
    private static final String username = "root"; // Username for the database
    private static final String password = ""; // Password for the database
    private static final String driver = "com.mysql.cj.jdbc.Driver";  // JDBC driver name

    public static Connection conn; // Connection object to manage the connection

    /**
     * Establishes a connection with the database and returns the connection object.
     *
     * @return The connection object, or {@code null} if the connection fails.
     */
    public static Connection makeConnection() {
        // Load and register the JDBC driver
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(DB_URL, username, password); // Connect to the database
            System.out.println("Connected to MySQL Database");
            return conn; // Return the connection object
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found " + e.getMessage()); // Exception handling if the driver class not found
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage()); // Handle exceptions related to the database connectivity
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            System.out.println("Please ensure that the database is set up correctly. Refer to the README for instructions.");
        }
        System.out.println("Connection to MySQL Database failedDatabase connection is not established. Please ensure that the database is set up correctly. Refer to the README for instructions.");
        return null; // If connection fails, return null
    }

    // New method to check the connection
    private static void checkConnection() {
        if (conn == null) {
            System.out.println("Database connection is not established. Please ensure that the database is set up correctly. Refer to the README for instructions.");

        }
    }

    /**
     * Converts the given date-time string to a {@code LocalDateTime} object.
     *
     * @param dateTimeStr The date-time string to convert.
     * @return The corresponding {@code LocalDateTime} object.
     */
    public static LocalDateTime convertTimeDateUTC(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        // Implement your UTC conversion here
        // ...
        return dateTime;
    }

    /**
     * Retrieves the username corresponding to the specified user ID.
     *
     * @param userId The ID of the user.
     * @return The username, or {@code null} if no user is found.
     * @throws SQLException If an error occurs while accessing the database.
     */
    public static String getUserNameById(int userId) throws SQLException {
        checkConnection();
        String query = "SELECT User_Name FROM client_schedule.users WHERE User_ID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("User_Name");
                } else {
                    return null; // return null if no user found
                }
            }
        }
    }

    // Method to save appointment
    public static void saveAppointment(Appointment appointment) throws SQLException {

        String sqlInsertAppointment = "INSERT INTO client_schedule.appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID, Created_By, Last_Updated_By) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsertAppointment)) {
            pstmt.setString(1, appointment.getAppointmentTitle());
            pstmt.setString(2, appointment.getAppointmentDescription());
            pstmt.setString(3, appointment.getAppointmentLocation());
            pstmt.setString(4, appointment.getAppointmentType());
            pstmt.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
            pstmt.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
            pstmt.setInt(7, appointment.getCustomerID());
            pstmt.setInt(8, appointment.getUserID());
            pstmt.setInt(9, appointment.getContactID()); // get the contactId from the Appointment object
            pstmt.setString(10, appointment.getCreatedBy());
            pstmt.setString(11, appointment.getLastUpdatedBy());

            pstmt.executeUpdate();
            System.out.println("Appointment " + appointment.getAppointmentTitle() + " has been added to the database.");
        } catch (SQLException ex) {
            System.err.println("Error while saving appointment: " + ex.getMessage());
        }
    }

    //method to get contact id by contact name
    public static int getContactIdByContactName(String contactName) throws SQLException {
        String query = "SELECT Contact_ID FROM client_schedule.contacts WHERE Contact_Name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, contactName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Contact_ID");
                } else {
                    return -1; // return -1 if no contact found
                }
            }
        }
    }

    //method to save customer
    public static void saveCustomer(Customer customer) throws SQLException {
        String sqlInsertCustomer = "INSERT INTO client_schedule.customers(Customer_Name, Address, Division_ID, Phone, Postal_Code) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsertCustomer)) {
            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getCustomerAddress());
            FirstLevelDivisions division = customer.getCustomerDivision();
            if (division != null) {
                pstmt.setInt(3, division.getDivisionId());

            } else {
                // Handle the case where the division is null.
                // Maybe you want to set the division ID to a default value, or throw an exception.
                throw new SQLException("Customer " + customer.getCustomerName() + " has null division");
            }            pstmt.setString(4, customer.getCustomerPhone());
            pstmt.setString(5, customer.getCustomerZip());
            pstmt.executeUpdate();
            System.out.println("Customer " + customer.getCustomerName() + " has been added to the database.");
        } catch (SQLException ex) {
            System.err.println("Error while saving customer: " + ex.getMessage());
        }
    }

    // Method to get all customers
    public static List<String> getAllCustomers() throws SQLException {
        String query = "SELECT Customer_Name FROM client_schedule.customers";

        try (Connection conn = DriverManager.getConnection(DB_URL, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<String> customers = new ArrayList<>();
            while (rs.next()) {
                customers.add(rs.getString("Customer_Name"));
            }

            return customers;
        }
    }

    // Method to get the current user ID
    public static int getCurrentUserId() {
        // manage the current user in the User class, so you can return the current user ID from there.
        return User.getUserID();
    }

    // Method to get a customer ID by customer name
    public static int getCustomerIdByCustomerName(String customerName) throws SQLException {
        String query = "SELECT Customer_ID FROM client_schedule.customers WHERE Customer_Name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customerName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Customer_ID");
                } else {
                    return -1; // return -1 if no customer found
                }
            }
        }
    }

    //method to get all contacts
    public static List<String> getAllContacts() throws SQLException {
        String query = "SELECT Contact_Name FROM client_schedule.contacts";

        try (Connection conn = DriverManager.getConnection(DB_URL, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<String> contacts = new ArrayList<>();
            while (rs.next()) {
                contacts.add(rs.getString("Contact_Name"));
            }

            return contacts;
        }
    }

    //method to get all countries
    public static List<Country> getAllCountries() throws SQLException {
        String query = "SELECT * FROM client_schedule.countries";

        try (Connection conn = DriverManager.getConnection(DB_URL, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<Country> countries = new ArrayList<>();
            while (rs.next()) {
                Country country = new Country(
                        rs.getInt("Country_ID"),
                        rs.getString("Country"),
                        rs.getTimestamp("Create_Date").toLocalDateTime(),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update"),
                        rs.getString("Last_Updated_By"));
                countries.add(country);
            }

            return countries;
        }
    }

    //method to get all divisions
    public static List<FirstLevelDivisions> getAllDivisions() throws SQLException {
        String query = "SELECT * FROM client_schedule.first_level_divisions";

        try (Connection conn = DriverManager.getConnection(DB_URL, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<FirstLevelDivisions> divisions = new ArrayList<>();
            while (rs.next()) {
                FirstLevelDivisions division = new FirstLevelDivisions(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getTimestamp("Create_Date").toLocalDateTime(),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update").toLocalDateTime(),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("COUNTRY_ID"));
                divisions.add(division);
            }

            return divisions;
        }
    }

    //method to get all division by country id
    public static List<FirstLevelDivisions> getAllDivisionsByCountryId(int countryId) throws SQLException {
        String query = "SELECT * FROM client_schedule.first_level_divisions WHERE COUNTRY_ID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, countryId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<FirstLevelDivisions> divisions = new ArrayList<>();
                while (rs.next()) {
                    FirstLevelDivisions division = new FirstLevelDivisions(
                            rs.getInt("Division_ID"),
                            rs.getString("Division"),
                            rs.getTimestamp("Create_Date").toString(),
                            rs.getString("Created_By"),
                            rs.getTimestamp("Last_Update").toString(),
                            rs.getString("Last_Updated_By"),
                            rs.getInt("COUNTRY_ID"));
                    divisions.add(division);
                }
                return divisions;
            }
        }
    }

    //method to update customer
    public static void updateCustomer(Customer customer) throws SQLException {
        String sqlUpdateCustomer = "UPDATE client_schedule.customers SET Customer_Name = ?, Address = ?, Division_ID = ?, Phone = ?, Postal_Code = ? WHERE Customer_ID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateCustomer)) {
            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getCustomerAddress());
            FirstLevelDivisions division = customer.getCustomerDivision();
            if (division != null) {
                pstmt.setInt(3, division.getDivisionId());
            } else {
                throw new SQLException("Customer " + customer.getCustomerName() + " has null division");
            }
            pstmt.setString(4, customer.getCustomerPhone());
            pstmt.setString(5, customer.getCustomerZip());
            pstmt.setInt(6, customer.getCustomerID());
            pstmt.executeUpdate();
            System.out.println("Customer " + customer.getCustomerName() + " has been updated in the database.");
        } catch (SQLException ex) {
            System.err.println("Error while updating customer: " + ex.getMessage());
        }
    }

    //method to get contact name by id
    public static String getContactNameById(int contactId) throws SQLException {
        String query = "SELECT Contact_Name FROM client_schedule.contacts WHERE Contact_ID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, contactId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Contact_Name");
                } else {
                    return null; // return null if no contact found
                }
            }
        }
    }

    //method to get customer name by id
    public static String getCustomerNameById(int customerId) throws SQLException {
        String query = "SELECT Customer_Name FROM client_schedule.customers WHERE Customer_ID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Customer_Name");
                } else {
                    return null; // return null if no customer found
                }
            }
        }
    }

    /**
     * Checks if an appointment overlaps with existing appointments for a given customer.
     *
     * @param customerId The customer ID to check.
     * @param startDateTime The start date-time of the appointment.
     * @param endDateTime The end date-time of the appointment.
     * @return {@code true} if an overlapping appointment is found, {@code false} otherwise.
     * @throws SQLException If an error occurs while accessing the database.
     */
    public static boolean doesAppointmentOverlap(int customerId, LocalDateTime startDateTime, LocalDateTime endDateTime) throws SQLException {
        String query = "SELECT * FROM client_schedule.appointments WHERE Customer_ID = ? AND ((Start <= ? AND End > ?) OR (Start < ? AND End >= ?) OR (Start >= ? AND End <= ?))";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            stmt.setTimestamp(2, Timestamp.valueOf(startDateTime));
            stmt.setTimestamp(3, Timestamp.valueOf(startDateTime));
            stmt.setTimestamp(4, Timestamp.valueOf(endDateTime));
            stmt.setTimestamp(5, Timestamp.valueOf(endDateTime));
            stmt.setTimestamp(6, Timestamp.valueOf(startDateTime));
            stmt.setTimestamp(7, Timestamp.valueOf(endDateTime));
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Return true if an overlapping appointment is found, false otherwise
            }
        }
    }

    /**
     * Updates an existing appointment in the database.
     *
     * @param updatedAppointment The updated appointment object.
     * @throws SQLException If an error occurs while updating the appointment.
     */
    public static void updateAppointment(Appointment updatedAppointment) throws SQLException {
        String sqlUpdateAppointment = "UPDATE client_schedule.appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?, Last_Updated_By = ? WHERE Appointment_ID = ?";


        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateAppointment)) {
            pstmt.setString(1, updatedAppointment.getAppointmentTitle());
            pstmt.setString(2, updatedAppointment.getAppointmentDescription());
            pstmt.setString(3, updatedAppointment.getAppointmentLocation());
            pstmt.setString(4, updatedAppointment.getAppointmentType());
            pstmt.setTimestamp(5, Timestamp.valueOf(updatedAppointment.getStart()));
            pstmt.setTimestamp(6, Timestamp.valueOf(updatedAppointment.getEnd()));
            pstmt.setInt(7, updatedAppointment.getCustomerID());
            pstmt.setInt(8, updatedAppointment.getUserID());
            pstmt.setInt(9, updatedAppointment.getContactID()); // get the contactId from the Appointment object
            pstmt.setString(10, updatedAppointment.getLastUpdatedBy());
            pstmt.setInt(11, updatedAppointment.getAppointmentID());

            pstmt.executeUpdate();
            System.out.println("Appointment " + updatedAppointment.getAppointmentTitle() + " has been updated in the database.");
        } catch (SQLException ex) {
            System.err.println("Error while updating appointment: " + ex.getMessage());
        }
    }

    /**
     * Closes the connection to the database.
     *
     * @throws SQLException If an error occurs while closing the connection.
     */
    public static void closeConnection() throws SQLException{
        conn.close(); // Close the connection
        System.out.println("Connection closed."); // Notify that the connection has been closed
    }
}

