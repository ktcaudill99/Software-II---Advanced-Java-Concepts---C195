
package schedule;

// import java.sql package to establish and manage connections with the database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    // This function closes the connection to the database
    public static void closeConnection() throws SQLException{
        conn.close(); // Close the connection
        System.out.println("Connection closed."); // Notify that the connection has been closed
    }
}

