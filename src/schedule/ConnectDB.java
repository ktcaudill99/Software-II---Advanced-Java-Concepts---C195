
package schedule;

// import java.sql package to establish and manage connections with the database
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//import static schedule.User.userID;


// This class is responsible for connecting with the database
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

    static Connection conn; // Connection object to manage the connection

    //This function establishes the connection with the database and returns the connection object
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
        }
        return null; // If connection fails, return null
    }
    public static void saveAppointment(Appointment appointment, int contactId) throws SQLException {
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
            pstmt.setInt(9, appointment.getContactID());
            pstmt.setString(10, appointment.getCreatedBy());
            pstmt.setString(11, appointment.getLastUpdatedBy());

            pstmt.executeUpdate();
            System.out.println("Appointment " + appointment.getAppointmentTitle() + " has been added to the database.");
        } catch (SQLException ex) {
            System.err.println("Error while saving appointment: " + ex.getMessage());
        }
    }

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
            pstmt.setInt(9, appointment.getContactId());  // get the contactId from the Appointment object
            pstmt.setString(10, appointment.getCreatedBy());
            pstmt.setString(11, appointment.getLastUpdatedBy());

            pstmt.executeUpdate();
            System.out.println("Appointment " + appointment.getAppointmentTitle() + " has been added to the database.");
        } catch (SQLException ex) {
            System.err.println("Error while saving appointment: " + ex.getMessage());
        }
    }

    public static int getCustomerIdByContactName(String contactName) throws SQLException {
        // Your SQL query here. This is just an example and may not work for your database
        String query = "SELECT Contact_ID FROM client_schedule.contacts WHERE Contact_Name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, contactName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Contact_ID");
                } else {
                    throw new SQLException("No customer found for contact: " + contactName);
                }
            }
        }
    }


    public class AddAppointmentController {
        // Assuming this method is where you are creating and saving the appointment
        public void createAndSaveAppointment() {
            // Initialize your appointment details here
            // Just as an example, using a constructor that takes these arguments
            Appointment appointment = new Appointment(1, 1, LocalDateTime.now(), "createdBy",
                    1, "description", LocalDateTime.now(), LocalDateTime.now(),
                    "lastUpdatedBy", "location", LocalDateTime.now(), "title",
                    "type", 1);

            // Save the appointment to the database
            try {
                ConnectDB.saveAppointment(appointment);
            } catch (SQLException e) {
                System.out.println("Error while saving the appointment: " + e.getMessage());
            }
        }
    }



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

    public Country getCountryById(int countryId) throws SQLException {
        // Placeholder for your actual SQL query to fetch country by ID
        String query = "SELECT * FROM client_schedule.countries WHERE country_id = ?";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, countryId);

        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            // Assume Country class has a constructor that takes all fields from the countries table
            Country country = new Country(rs.getInt("country_id"), rs.getString("Country"));

            return country;
        }

        return null; // Return null if no country found
    }


    public static List<String> getAllContacts() throws SQLException {
        String query = "SELECT Contact_Name FROM client_schedule.contacts"; // Adjust this query to match your actual database

        try (Connection conn = DriverManager.getConnection(DB_URL, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<String> contacts = new ArrayList<>();
            while (rs.next()) {
                contacts.add(rs.getString("Contact_Name")); // Adjust this to match your actual database
            }

            return contacts;
        }
    }

    // Add a new method to get a contact ID by contact name
    public static int getContactIdByName(String contactName) throws SQLException {
        String query = "SELECT Contact_ID FROM client_schedule.contacts WHERE Contact_Name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, contactName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Contact_ID");
                } else {
                    throw new SQLException("No contact found with name: " + contactName);
                }
            }
        }
    }

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
            pstmt.setInt(6, customer.getCustomerID());  // assuming a method getCustomerId() exists in the Customer class
            pstmt.executeUpdate();
            System.out.println("Customer " + customer.getCustomerName() + " has been updated in the database.");
        } catch (SQLException ex) {
            System.err.println("Error while updating customer: " + ex.getMessage());
        }
    }

    public FirstLevelDivisions getDivisionById(int divisionId) throws SQLException {
        // Placeholder for your actual SQL query to fetch division by ID
        String query = "SELECT * FROM client_schedule.first_level_divisions WHERE Division_ID = ?";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, divisionId);

        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            // Assume FirstLevelDivisions class has a constructor that takes all fields from the first_level_divisions table
            FirstLevelDivisions division = new FirstLevelDivisions(
                    rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getTimestamp("Create_Date").toLocalDateTime(),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update").toLocalDateTime(),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("COUNTRY_ID"));

            return division;
        }

        return null; // Return null if no division found
    }



    // This function closes the connection to the database
    public static void closeConnection() throws SQLException{
        conn.close(); // Close the connection
        System.out.println("Connection closed."); // Notify that the connection has been closed
    }
}

