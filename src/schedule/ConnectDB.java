/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Instant;
import java.io.IOException;
import java.sql.SQLException;


/**
 *
 * @author Katie-BAMF
 */
public class ConnectDB {
    
      //variables used to connect to database
    private static final String databaseName = "wgu";
  //  private static final String DB_URL = "jdbc:mysql://52.206.157.109:3306/" + databaseName;
   private static final String DB_URL = "jdbc:mysql://localhost/" + databaseName;
   private static final String username = "root";
   // private static final String password = "";
    private static final String driver = "com.mysql.jdbc.Driver";
    
   static Connection conn;
    
    //establishes connection to the database 
    public static Connection makeConnection() throws ClassNotFoundException, SQLException, Exception{
        Class.forName(driver);
        conn = DriverManager.getConnection(DB_URL);
        System.out.println("Connection successful.");
        return conn;
    }
    
    //closes database connection
    public static void closeConnection() throws SQLException{
        conn.close();
        System.out.println("Connection closed.");
    }
}
