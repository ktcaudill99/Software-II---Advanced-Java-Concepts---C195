/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


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

    private Customer selectedCustomer;

    private Appointment selectedAppointment;


    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

   // private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    // private static ObservableList<Appointment> allAppointmentsByMonth = FXCollections.observableArrayList();
  //  private static ObservableList<Appointment> allAppointmentsByWeek = FXCollections.observableArrayList();
    private ScheduleService service;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.getAllCustomers();
        this.tvCustomers.setItems(allCustomers);

        this.customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        this.name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        this.address.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
       // this.state.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
        this.phoneNumber.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        this.postal.setCellValueFactory(new PropertyValueFactory<>("customerZip"));
      //  this.tvCustomers.setItems(getAllCustomers());


        this.getAllAppointments();
        this.tvAppointments.setItems(getAllAppointments());

        this.colAppID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        this.colContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        this.colStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        this.colTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        this.colType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        this.colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        this.colDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        this.colEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        this.colLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        this.colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));


        this.service = new ScheduleService(allAppointments, allCustomers);

        this.tvCustomers.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        this.selectedCustomer = newSelection;
                    }
                }
        );


        this.tvAppointments.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        this.selectedAppointment = newSelection;
                    }
                }
        );

    }    

    @FXML
    private void addAppAction(ActionEvent event) throws IOException {
            Stage stage;
            Parent root;
            stage = (Stage) btnAppAdd.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("AddAppointment.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    @FXML
    private void modifyAppAction(ActionEvent event) throws IOException {
        if (selectedAppointment != null) {
            System.out.println("Selected Customer: " + selectedAppointment.getAppointmentID()); // Debug line
            // Load the modify customer screen
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyAppointment.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                // Pass the selected customer to the modify customer controller
                ModifyAppointmentController controller = loader.getController();
                controller.setAppointment(selectedAppointment);
            } catch (IOException ex) {
                System.err.println("Error loading modify customer screen: " + ex.getMessage()); // Debug line
            }
        } else {
            // Inform the user that no customer was selected
        }
    }

    @FXML
    private void deleteAppAction(ActionEvent event) throws SQLException {
        if (selectedAppointment != null) {
        System.out.println("Selected Appointment: " + selectedAppointment.getAppointmentID()); // Debug line

        // Confirm deletion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Appointment");
        alert.setContentText("Are you sure you want to delete this appointment?");

        // Show alert and wait for user to close it
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        // Only proceed with deletion if OK was clicked
        if (result == ButtonType.OK) {
            String sqlDeleteAppointments = "DELETE FROM client_schedule.appointments WHERE appointment_ID = ?";
            try (PreparedStatement pstmtAppointments = ConnectDB.conn.prepareStatement(sqlDeleteAppointments))
                {
                // Start a transaction
                ConnectDB.conn.setAutoCommit(false);
                // Delete appointments
                pstmtAppointments.setInt(1, selectedAppointment.getAppointmentID());
                int rowsAffectedAppointments = pstmtAppointments.executeUpdate(); // Returns number of affected rows
                System.out.println("Appointments Deleted: " + rowsAffectedAppointments); // Debug line
                // Commit transaction
                ConnectDB.conn.commit();
                // Remove appointment from list
                allAppointments.remove(selectedAppointment);

                // Also, remove this customer's appointments from the allAppointments list
                tvAppointments.refresh();

            } catch (SQLException ex) {
                // If there was an error then rollback the changes
                if (ConnectDB.conn != null) {
                    try {
                        System.err.println("Transaction is being rolled back due to: " + ex.getMessage()); // Debug line
                        ConnectDB.conn.rollback();
                    } catch (SQLException e) {
                        // Handle exception
                        System.err.println("Error during rollback: " + e.getMessage()); // Debug line
                    }
                }
            } finally {
                try {
                    ConnectDB.conn.setAutoCommit(true);
                } catch (SQLException e) {
                    // Handle exception
                    System.err.println("Error setting auto commit: " + e.getMessage()); // Debug line
                }
            }
        }
    } else {
        // Inform the user that no customer was selected
    }
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
        if (selectedCustomer != null) {
            System.out.println("Selected Customer: " + selectedCustomer.getCustomerID()); // Debug line
            // Load the modify customer screen
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyCustomer.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                // Pass the selected customer to the modify customer controller
                ModifyCustomerController controller = loader.getController();
                controller.setCustomer(selectedCustomer);
            } catch (IOException ex) {
                System.err.println("Error loading modify customer screen: " + ex.getMessage()); // Debug line
            }
        } else {
            // Inform the user that no customer was selected
        }

    }

    @FXML
    private void deleteCustomerAction(ActionEvent event) throws SQLException {
        if (selectedCustomer != null) {
            int customerID = selectedCustomer.getCustomerID(); // store customer ID for debugging
            System.out.println("Selected Customer: " + customerID); // Debug line

            // Confirm deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Customer");
            alert.setContentText("Are you sure you want to delete this customer?");

            // Show alert and wait for user to close it
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

            // Only proceed with deletion if OK was clicked
            if (result == ButtonType.OK) {
                String sqlDeleteAppointments = "DELETE FROM client_schedule.appointments WHERE customer_ID = ?";
                String sqlDeleteCustomer = "DELETE FROM client_schedule.customers WHERE customer_ID = ?";
                try (PreparedStatement pstmtAppointments = ConnectDB.conn.prepareStatement(sqlDeleteAppointments);
                     PreparedStatement pstmtCustomer = ConnectDB.conn.prepareStatement(sqlDeleteCustomer)) {

                    // Start a transaction
                    ConnectDB.conn.setAutoCommit(false);

                    // Delete appointments
                    pstmtAppointments.setInt(1, customerID);
                    int rowsAffectedAppointments = pstmtAppointments.executeUpdate(); // Returns number of affected rows
                    System.out.println("Appointments Deleted: " + rowsAffectedAppointments); // Debug line

                    // Delete customer
                    pstmtCustomer.setInt(1, customerID);
                    int rowsAffectedCustomer = pstmtCustomer.executeUpdate(); // Returns number of affected rows
                    System.out.println("Customers Deleted: " + rowsAffectedCustomer); // Debug line

                    // Commit transaction
                    ConnectDB.conn.commit();

                    // Remove customer from list
                    allCustomers.remove(selectedCustomer);

                    // Also, remove this customer's appointments from the allAppointments list
                    allAppointments.removeIf(appointment -> {
                        boolean isRemoved = appointment.getCustomerID() == customerID;
                        if (isRemoved) {
                            System.out.println("Removing appointment with ID: " + appointment.getAppointmentID());
                        }
                        return isRemoved;
                    });
                    tvAppointments.refresh();

                } catch (SQLException ex) {
                    // If there was an error then rollback the changes
                    if (ConnectDB.conn != null) {
                        try {
                            System.err.println("Transaction is being rolled back due to: " + ex.getMessage()); // Debug line
                            ConnectDB.conn.rollback();
                        } catch (SQLException e) {
                            // Handle exception
                            System.err.println("Error during rollback: " + e.getMessage()); // Debug line
                        }
                    }
                } finally {
                    try {
                        ConnectDB.conn.setAutoCommit(true);
                    } catch (SQLException e) {
                        // Handle exception
                        System.err.println("Error setting auto commit: " + e.getMessage()); // Debug line
                    }
                }
            }
        } else {
            // Inform the user that no customer was selected
        }
    }






    //get all customers from database and add to observable list for tableview
    public void getAllCustomers() {
        System.out.println("Retrieving Customer Records");
        allCustomers.clear();

        try {
            Statement statement = ConnectDB.conn.createStatement();
            String query = "SELECT Customer_ID, Customer_Name, Address, Division_ID, Phone, Postal_Code FROM client_schedule.customers";
            ResultSet results = statement.executeQuery(query);

            while(results.next()) {
                Customer customer = new Customer(results.getInt("Customer_ID"), results.getString("Customer_Name"), results.getString("Address"), results.getInt("Division_ID"), results.getString("Phone"), results.getString("Postal_Code"));
                allCustomers.add(customer);
                System.out.println("Customer ID: " + results.getInt("Customer_ID"));
            }

            statement.close();
        } catch (SQLException var4) {
            System.out.println("Cannot retrieve Customers: " + var4.getMessage());
        }
    }


    public ObservableList<Appointment> getAllAppointments() {
        System.out.println("Retrieving Appointment Records");
        allAppointments.clear();

        try {
            Statement statement = ConnectDB.conn.createStatement();
            String query = "SELECT Appointment_ID, Contact_ID, Create_Date, Created_By, Customer_ID, Description, End, Last_Update, Last_Updated_By, Location, Start, Title, Type, User_ID FROM client_schedule.appointments";
            ResultSet results = statement.executeQuery(query);


            while (results.next()) {
                Appointment appointment = new Appointment(
                        results.getInt("Appointment_ID"),
                        results.getInt("Contact_ID"),
                        results.getTimestamp("Create_Date").toLocalDateTime(), // Convert Timestamp to LocalDateTime
                        results.getString("Created_By"),
                        results.getInt("Customer_ID"),
                        results.getString("Description"),
                        results.getTimestamp("End").toLocalDateTime(), // Convert Timestamp to LocalDateTime
                        results.getTimestamp("Last_Update").toLocalDateTime(), // Convert Timestamp to LocalDateTime
                        results.getString("Last_Updated_By"),
                        results.getString("Location"),
                        results.getTimestamp("Start").toLocalDateTime(), // Convert Timestamp to LocalDateTime
                        results.getString("Title"),
                        results.getString("Type"),
                        results.getInt("User_ID")
                );
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
