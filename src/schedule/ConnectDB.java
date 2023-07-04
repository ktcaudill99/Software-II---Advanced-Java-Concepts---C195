///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package schedule;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//
///**
// *
// * @author Katie-BAMF
// */
//public class ConnectDB {
//
//      //variables used to connect to database
//    private static final String databaseName = "wgu";
//  //  private static final String DB_URL = "jdbc:mysql://52.206.157.109:3306/" + databaseName;
//   private static final String DB_URL = "jdbc:mysql://localhost/" + databaseName;
//   private static final String username = "root";
//    private static final String password = "";
//    private static final String driver = "com.mysql.cj.jdbc.Driver";  // New driver class for MySQL Connector/J 8.0 and later
//   static Connection conn;
//
//    //establishes connection to the database
////    public static Connection makeConnection() throws ClassNotFoundException, SQLException, Exception{
////        Class.forName(driver);
////        conn = DriverManager.getConnection(DB_URL);
////        System.out.println("Connection successful.");
////        return conn;
////    }
////
//
//    public static Connection makeConnection() {
//        try {
//            Class.forName(driver);
//            conn = DriverManager.getConnection(DB_URL, username, password);
//            System.out.println("Connected to MySQL Database");
//            return conn;  // Return the connection object
//        } catch (ClassNotFoundException e) {
//            System.out.println("Class Not Found " + e.getMessage());
//        } catch (SQLException e) {
//            System.out.println("SQLException: " + e.getMessage());
//            System.out.println("SQLState: " + e.getSQLState());
//            System.out.println("VendorError: " + e.getErrorCode());
//        }
//        return null;
//    }
//
//
//    //closes database connection
//    public static void closeConnection() throws SQLException{
//        conn.close();
//        System.out.println("Connection closed.");
//    }
//}
package schedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

    private static final String databaseName = "mysql";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + databaseName
            + "?verifyServerCertificate=false"
            + "&useSSL=true"
            + "&requireSSL=true";
    private static final String username = "root";
    private static final String password = "";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    static Connection conn;

    public static Connection makeConnection() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(DB_URL, username, password);
            System.out.println("Connected to MySQL Database");
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return null;
    }

    public static void closeConnection() throws SQLException{
        conn.close();
        System.out.println("Connection closed.");
    }
}
