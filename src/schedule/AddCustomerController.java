///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
///*
//package schedule;
//
//import schedule.ConnectDB;
//import schedule.User;
//import schedule.CountryDB
//
//import schedule.CountryDB.getAllCountries;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.Optional;
//import java.util.ResourceBundle;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.TextField;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
//import javafx.stage.Stage;
//
//import java.net.URL;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.util.MissingResourceException;
//import java.util.logging.Level;
//
//
//
//
//
///**
// * FXML Controller class
// *
// * @author Katie-BAMF
// */
//public class AddCustomerController implements Initializable {
//    
//     ObservableList<FirstLevelDivisions> filteredDivisions = FXCollections.observableArrayList();
//     ObservableList<Country> countries = countryDB.getAllCountries();
//     
//    private Stage stage;
//    private Parent scene;
//
//    @FXML
//    private TextField name;
//    @FXML
//    private TextField address;
//    @FXML
//    private TextField zip;
//    @FXML
//    private TextField phone;
//    
//     @FXML
//    private Button btnSave;
//    @FXML
//    private Button btnCancel;
//    
//    
//    @FXML
//    private ComboBox<FirstLevelDivisions> divisionCB;
//    
//  
//  
//    @FXML
//    private ComboBox<Country> countryCB;
//    
//    
//   //private CountryDAOImpl countryDB = new CountryDAOImpl();
//    
////    @FXML 
// //   private ObservableList<Country> countries = countryDB.getAllCountries();
//    private Object CountryDB;
//    
//    
//
//    /**
//     * Initializes the controller class.
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        //conect to database
//        //populate drop down menus
//          //create statement object
//  /*        try{
//        Statement statement = ConnectDB.conn.createStatement();
//        
//        String sqlStatement = "SELECT Country_ID, Country from wgu.countries";
//        
//        
//         
//        
//        
//          } catch(SQLException e)
//          {System.out.println("error: Statement statement = ConnectDB.conn.createStatement();");
//          
//          }
//          */
//          /*
//            try{
//        Statement statement = ConnectDB.conn.createStatement();
//        
//        String sqlStatement = "SELECT Country_ID, Country from wgu.countries";
//
//       // pull from database and populate country drop down menu
//        ResultSet result = statement.executeQuery(sqlStatement);
//
//        while(result.next()){
//            countryCB.getItems().add(result.getString("Country"));
//        }
//
//        //close statement
//
//        statement.close();
//        }catch(SQLException e){
//            System.out.println("Error: " + e.getMessage());
//        }
//  
//  
//          
//          */
//          
//       ObservableList<Country> countryData = getCountryList();
//        countryCB.setItems(countryData);
//        countryCB.getSelectionModel().selectFirst();
//        filterDivisions();
//          
//          
//    }
//        
//        
//        
//        
///*
//        //gets User ID for current user
//    private int getDivsion(String userName) throws SQLException {
//        int userID = -1;
//
//        //create statement object
//        Statement statement = ConnectDB.conn.createStatement();
//
//        //write SQL statement
//        String sqlStatement = "SELECT userID FROM user WHERE userName ='" + userName + "'";
//
//        //create resultset object
//        ResultSet result = statement.executeQuery(sqlStatement);
//
//        while (result.next()) {
//            userID = result.getInt("userId");
//        }
//        return userID;
//    }
//    */
//    
//    @FXML
//    private void setCountry(ActionEvent event) {
//
//        
//    }
//
//    @FXML
//    private void partSaveButtonAction(ActionEvent event) {
//        
//          if(name.getText().isEmpty() || address.getText().isEmpty()|| zip.getText().isEmpty() ||
//           phone.getText().isEmpty()) {
//            
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Warning!");
//            alert.setContentText("All Fields are Required.  Please ensure no fields have been left blank.");
//            alert.showAndWait();
//        }
//        else {
//            LocalDateTime now = LocalDateTime.now();
//            ZonedDateTime currentUTC = now.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));
//
//            String sCurrentDT = currentUTC.toLocalDateTime().toString().replace("T", " ");
//
//            try {
//                    PreparedStatement pscust = ConnectDB.getConn().prepareStatement("INSERT INTO customers "
//                    + "(customer_Name, address, postal_code, phone, create_Date, created_By, last_Update, last_Updated_By, division_id )"
//                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
//
//                    pscust.setString(1, name.getText());
//                    pscust.setString(2, address.getText());
//                    pscust.setString(3, zip.getText());
//                    pscust.setString(4, phone.getText());
//                    pscust.setString(5, sCurrentDT);
//                    pscust.setString(6, globalUser);
//
//                    pscust.setString(7, sCurrentDT);
//                    pscust.setString(8, globalUser);
//                    pscust.setInt(9, divisionCB.getSelectionModel().getSelectedItem().getDivisionID());
//                    int result = pscust.executeUpdate();
//                    if (result == 1) {
//                        System.out.println("Good! New Customer Saved");
//                        LOGGER.log(Level.INFO, "New Cusomter Saved {0}", customerNmFld.getText());
//                    } else {
//                        System.out.println("Sorry! New Customer Erred");
//                        LOGGER.log(Level.INFO, "New Cusomter Erred {0}", customerNmFld.getText());
//                    }
//
//                } catch (SQLException ex) {
//                ex.printStackTrace();
//                }
//            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
//            scene = (Parent)FXMLLoader.load(this.getClass().getResource("/schedule/home.fxml"));
//            stage.setScene(new Scene(this.scene));
//            stage.show();
//        
//          }
//        
//    }
//
//    @FXML
//    private void cancelPartAction(ActionEvent event)throws IOException {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit and\ndiscard changes?", new ButtonType[0]);
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
//            scene = (Parent)FXMLLoader.load(this.getClass().getResource("/schedule/home.fxml"));
//            stage.setScene(new Scene(this.scene));
//            stage.show();
//        }
//    }
//
//    @FXML
//   void onActionSelectCountry(ActionEvent event) {
//        filterDivisions();
//    }
//   
//   
//   
//   
//      /**
//     * The method created to filter the divisions after the user selects the country 
//     */
//    public void filterDivisions(){
//        filteredDivisions.clear();
//        int country_ID = countryCB.getSelectionModel().getSelectedItem().getCountryId();
//        for(FirstLevelDivisions fld : DataProvider.getAllDivisions()){
//            if(country_ID == fld.getCountryId()){
//                filteredDivisions.add(fld);
//            }
//                
//        }
//        divisionCB.setItems(filteredDivisions);
//        divisionCB.getSelectionModel().selectFirst();
//
//        
//    }
//    
//}
//
///*
//   //gets User ID for current user
//    private int getUserID(String userName) throws SQLException {
//        int userID = -1;
//
//        //create statement object
//        Statement statement = ConnectDB.conn.createStatement();
//
//        //write SQL statement
//        String sqlStatement = "SELECT userID FROM user WHERE userName ='" + userName + "'";
//
//        //create resultset object
//        ResultSet result = statement.executeQuery(sqlStatement);
//
//        while (result.next()) {
//            userID = result.getInt("userId");
//        }
//        return userID;
//    }
//    
//    private boolean correctPassword(int userID, String password) throws SQLException {
//
//        //create statement object
//        Statement statement = ConnectDB.conn.createStatement();
//
//        //write SQL statement
//        String sqlStatement = "SELECT password FROM user WHERE userId ='" + userID + "'";;
//
//        //create resultset object
//        ResultSet result = statement.executeQuery(sqlStatement);
//
//        while (result.next()) {
//            if (result.getString("password").equals(password)) {
//                return true;
//            }
//        }
//        return false;
//    }
//*/