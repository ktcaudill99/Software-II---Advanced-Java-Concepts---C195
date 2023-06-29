/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import java.sql.Connection;
//import controller.errorMessage;
import schedule.Country;
import java.sql.*;
import java.time.LocalDateTime;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;


import schedule.Country;
import  schedule.FXMain;

/**
 *
 * @author Katie-BAMF
 */
public class CountryDB {
    
    
    private Stage stage;
    private Parent scene;
    
    
private final Connection conn = FXMain.conn;
    private final String selectAllCountries = "SELECT * FROM countries";
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
   
    private ObservableList<Country> countries = FXCollections.observableArrayList();

    public CountryDB() throws SQLException {
        QueryDatabase.setPreparedStatement(conn, selectAllCountries);
        preparedStatement = QueryDatabase.getPreparedStatement();
        resultSet = preparedStatement.executeQuery();

    }
  
    
    public ObservableList<Country> getAllCountries() throws IOException {
        try {
            while (resultSet.next()) {
                int countryId = resultSet.getInt("Country ID");
                String countryName = resultSet.getString("Country Name");
                LocalDateTime createdDate = resultSet.getTimestamp("Created Date").toLocalDateTime();
                String createdBy = resultSet.getString("Created By");
                Timestamp lastUpdated = resultSet.getTimestamp("Last Updated");
                String lastUpdatedBy = resultSet.getString("Last Updated By");
                Country country = new Country(countryId, countryName, createdDate, createdBy, lastUpdated, lastUpdatedBy);
                countries.add(country);
            }
        } catch (SQLException e) {
         System.out.println("Error");
        
        }
        return countries;
    }


    public Country getCountry(int countryId) throws SQLException {
        Country country = null;
        String selectCountry = "SELECT * FROM countries WHERE Country ID=" + countryId;
        QueryDatabase.setPreparedStatement(conn, selectCountry);
        PreparedStatement preparedStatement = QueryDatabase.getPreparedStatement();
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String countryName = resultSet.getString("Country");
            LocalDateTime createdDate = resultSet.getTimestamp("Created Date").toLocalDateTime();
            String createdBy = resultSet.getString("Created By");
            Timestamp lastUpdated = resultSet.getTimestamp("Last Updated");
            String lastUpdatedBy = resultSet.getString("Last Updated By");
            country = new Country(countryId, countryName, createdDate, createdBy, lastUpdated, lastUpdatedBy);
        }
        return country;
    }

    static class getAllCountries {

        public getAllCountries() {
        }
    }
    
}
