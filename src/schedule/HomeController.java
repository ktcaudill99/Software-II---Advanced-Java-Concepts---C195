/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jdk.jfr.Description;


/**
 * FXML Controller class
 *
 * @author Katie-BAMF
 */
public class HomeController implements Initializable {

    @FXML
    private TableView<Appointment> tvAppointments;
    @FXML
    private TableColumn<Appointment, Integer> colAppID;
    @FXML
    private TableColumn<Appointment, String> colTitle;
    @FXML
    private TableColumn<Appointment, String> colDescription;
    @FXML
    private TableColumn<Appointment, String> colLocation;
    @FXML
    private TableColumn<Appointment, String> colContact;
    @FXML
    private TableColumn<Appointment, String> colType;
    @FXML
    private TableColumn<Appointment, String> colStart;
    @FXML
    private TableColumn<Appointment, String> colEnd;
    @FXML
    private TableColumn<Appointment, Integer> colCustomerID;
    @FXML
    private TableColumn<Appointment, Integer> colUserID;
    @FXML
    private Button btnAppAdd;
    @FXML
    private Button btnAppMod;
    @FXML
    private Button btnAppDel;
    @FXML
    private TableView<Customer> tvCustomers;
    @FXML
    private TableColumn<Customer, Integer> customerID;
    @FXML
    private TableColumn<Customer, String> name;
    @FXML
    private TableColumn<Customer, String> phoneNumber;
    @FXML
    private TableColumn<Customer, String> address;
    @FXML
    private TableColumn<Customer, String> state;
    @FXML
    private TableColumn<Customer, String> postal;
    @FXML
    private Button btnCustomerAdd;
    @FXML
    private Button btnCustomerMod;
    @FXML
    private Button btnCustomerDel;
    @FXML
    private Button report;
    @FXML
    private Button logout;

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();


   // private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    // private static ObservableList<Appointment> allAppointmentsByMonth = FXCollections.observableArrayList();
  //  private static ObservableList<Appointment> allAppointmentsByWeek = FXCollections.observableArrayList();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.customerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        this.name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        this.address.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
       // this.state.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
        this.phoneNumber.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        this.postal.setCellValueFactory(new PropertyValueFactory<>("customerZip"));
        this.tvCustomers.setItems(getAllCustomers());



        this.colAppID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        this.colTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        this.colDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        this.colLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        this.colContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        this.colType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        this.colStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        this.colEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        this.colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        this.colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        this.tvAppointments.setItems(getAllAppointments());

    }    

    @FXML
    private void addAppAction(ActionEvent event) {
    }

    @FXML
    private void modifyAppAction(ActionEvent event) {
    }

    @FXML
    private void deleteAppAction(ActionEvent event) {
    }

    @FXML
    private void addCustomerAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) btnCustomerAdd.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifyCustomerAction(ActionEvent event) {
    }

    @FXML
    private void deleteCustomerAction(ActionEvent event) {
    }



    //get all customers from database and add to observable list for tableview
    public static ObservableList<Customer> getAllCustomers() {
        System.out.println("Retrieving Customer Records");
        allCustomers.clear();

        try {
            Statement statement = ConnectDB.conn.createStatement();
            String query = "SELECT Customer_ID, Customer_Name, Address, Division_ID, Phone, Postal_Code FROM client_schedule.customers";
            ResultSet results = statement.executeQuery(query);

            while(results.next()) {
                Customer customer = new Customer(results.getInt("Customer_ID"), results.getString("Customer_Name"), results.getString("Address"), results.getString("Division_ID"), results.getString("Phone"), results.getString("Postal_Code"));
                allCustomers.add(customer);
                System.out.println("Customer ID: " + results.getInt("Customer_ID"));
            }

            statement.close();
            return allCustomers;
        } catch (SQLException var4) {
            System.out.println("Cannot retrieve Customers: " + var4.getMessage());
            return null;
        }



        }

    public static ObservableList<Appointment> getAllAppointments() {
        System.out.println("Retrieving Appointment Records");
        allAppointments.clear();

        try {
            Statement statement = ConnectDB.conn.createStatement();
            String query = "SELECT Appointment_ID, Contact_ID, Create_Date, Created_By, Customer_ID, Description, End, Last_Update, Last_Updated_By, Location, Start, Title, Type, User_ID FROM client_schedule.appointments";
            ResultSet results = statement.executeQuery(query);


            while (results.next()) {
                Appointment appointment = new Appointment(results.getInt("Appointment_ID"), results.getInt("Contact_ID"), results.getString("Create_Date"), results.getString("Created_By"), results.getInt("Customer_ID"), results.getString("Description"), results.getString("End"), results.getString("Last_Update"), results.getString("Last_Updated_By"), results.getString("Location"), results.getString("Start"), results.getString("Title"), results.getString("Type"), results.getInt("User_ID"));
                allAppointments.add(appointment);
                System.out.println("Appointment ID: " + results.getInt("Appointment_ID"));
            }

            statement.close();
            return allAppointments;
        } catch (SQLException var4) {
            System.out.println("Cannot retrieve Appointments: " + var4.getMessage());
            return null;
        }

    }





    }













