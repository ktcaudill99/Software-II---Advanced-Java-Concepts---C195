//purpose: controller for home screen
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import constructors.Appointment;
import constructors.Customer;
import constructors.FirstLevelDivisions;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.ConnectDB;
import main.ScheduleService;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.stream.Collectors;
import java.sql.Timestamp;




/**
 *
 *
 *
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
    private ScheduleService service;
    @FXML
    private RadioButton rbCurrentMonth;
    @FXML
    private RadioButton rbCurrentWeek;
    @FXML
    private RadioButton rbAllAppointments;
    @FXML
    private ToggleGroup radioButtonToggleGroup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Set the toggle group for the radio buttons
        radioButtonToggleGroup = new ToggleGroup();

        rbCurrentMonth.setToggleGroup(radioButtonToggleGroup);
        rbCurrentWeek.setToggleGroup(radioButtonToggleGroup);
        rbAllAppointments.setToggleGroup(radioButtonToggleGroup);
        rbAllAppointments.setSelected(true);

        this.getAllCustomers();
        this.tvCustomers.setItems(allCustomers);

        // Set the cell value factories for the customer table
        this.customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        this.name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        this.address.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));

        this.state.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Customer, String> param) {
                return new SimpleStringProperty(param.getValue().getCustomerDivision().getDivision());
            }
        });

        this.phoneNumber.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        this.postal.setCellValueFactory(new PropertyValueFactory<>("customerZip"));

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

    //check for appointments within 15 minutes of login
    public void checkUpcomingAppointments() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threshold = now.plusMinutes(15);
        boolean hasUpcomingAppointment = false;

        for (Appointment appointment : allAppointments) {
            LocalDateTime appointmentStart = appointment.getStart();
            if (appointmentStart.isAfter(now) && appointmentStart.isBefore(threshold)) {
                hasUpcomingAppointment = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointment");
                alert.setHeaderText("You have an upcoming appointment!");
                alert.setContentText("Appointment ID: " + appointment.getAppointmentID()
                        + "\nDate: " + appointmentStart.toLocalDate()
                        + "\nTime: " + appointmentStart.toLocalTime());
                alert.show();
                break;
            }
        }

        if (!hasUpcomingAppointment) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Upcoming Appointments");
            alert.setHeaderText("You have no upcoming appointments in the next 15 minutes.");
            alert.show();
        }
    }

    //add appointment button
    @FXML
    private void addAppAction(ActionEvent event) throws IOException {
        Parent addAppointmentParent = FXMLLoader.load(getClass().getResource("../views/AddAppointment.fxml"));
        Scene addAppointmentScene = new Scene(addAppointmentParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addAppointmentScene);
        window.show();
    }

    //modify appointment button
    @FXML
    private void modifyAppAction(ActionEvent event) throws IOException {
        if (selectedAppointment != null) {
            System.out.println("Selected Appointment: " + selectedAppointment.getAppointmentID()); // Debug line

            // Load the modify appointment screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ModifyAppointment.fxml"));
            Parent root = loader.load();

            // Pass the selected appointment to the modify appointment controller
            ModifyAppointmentController controller = loader.getController();
            controller.setAppointment(selectedAppointment);

            // Set the scene and stage
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnAppMod.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } else {
            // Inform the user that no appointment was selected
            System.out.println("No appointment selected.");
        }
    }

    //radio buttons for appointment view
    @FXML
    private void currentMonthAction(ActionEvent event) {
        if (rbCurrentMonth.isSelected()) {
            filterAppointmentsByMonth();
        } else {
            tvAppointments.setItems(allAppointments);
        }
    }

    @FXML
    private void currentWeekAction(ActionEvent event) {
        if (rbCurrentWeek.isSelected()) {
            filterAppointmentsByWeek();
        } else {
            tvAppointments.setItems(allAppointments);
        }
    }

    @FXML
    private void allAppointmentsAction(ActionEvent event) {
        if (rbAllAppointments.isSelected()) {
            tvAppointments.setItems(allAppointments);
        }
    }
    private void filterAppointmentsByMonth() {
        // Get the current month
        Month currentMonth = LocalDate.now().getMonth();

        // Filter the appointments for the current month
        ObservableList<Appointment> filteredAppointments = allAppointments.stream()
                .filter(app -> app.getStart().getMonth() == currentMonth)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        // Update the table view
        tvAppointments.setItems(filteredAppointments);
    }

    private void filterAppointmentsByWeek() {
        // Get the current week of the year
        int currentWeek = LocalDate.now().get(WeekFields.ISO.weekOfWeekBasedYear());

        // Filter the appointments for the current week
        ObservableList<Appointment> filteredAppointments = allAppointments.stream()
                .filter(app -> app.getStart().get(WeekFields.ISO.weekOfWeekBasedYear()) == currentWeek)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        // Update the table view
        tvAppointments.setItems(filteredAppointments);
    }

    //delete appointment button
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
            System.out.println("No customer selected."); // Debug line
    }
    }

    //add customer button
    @FXML
    private void addCustomerAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) btnCustomerAdd.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../views/AddCustomer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //modify customer button
    @FXML
    private void modifyCustomerAction(ActionEvent event) {
        if (selectedCustomer != null) {
            System.out.println("Selected Customer: " + selectedCustomer.getCustomerID()); // Debug line
            // Load the modify customer screen
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ModifyCustomer.fxml"));
                Parent root = loader.load();

                // Pass the selected customer to the modify customer controller
                ModifyCustomerController controller = loader.getController();
                controller.setCustomer(selectedCustomer);

                // Set the scene and stage
                Scene scene = new Scene(root);
                Stage stage = (Stage) btnCustomerMod.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.err.println("Error loading modify customer screen: " + ex.getMessage()); // Debug line
            }
        } else {
            // Inform the user that no customer was selected
            System.out.println("No customer selected."); // Debug line
        }
    }

    //delete customer button
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

    //get division by id
    public FirstLevelDivisions getFirstLevelDivisionById(int divisionId) {
        FirstLevelDivisions division = null;

        try {
            String query = "SELECT * FROM client_schedule.first_level_divisions WHERE Division_ID = ?";
            PreparedStatement preparedStatement = ConnectDB.conn.prepareStatement(query);
            preparedStatement.setInt(1, divisionId);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                division = new FirstLevelDivisions(results.getInt("Division_ID"), results.getString("Division"), results.getInt("Country_ID"));
            }

            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Cannot retrieve Division: " + e.getMessage());
        }

        return division;
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
                int divisionId = results.getInt("Division_ID");
                FirstLevelDivisions division = getFirstLevelDivisionById(divisionId);

                if (division != null) {
                    Customer customer = new Customer(results.getInt("Customer_ID"), results.getString("Customer_Name"), results.getString("Address"), division, results.getString("Phone"), results.getString("Postal_Code"));
                    allCustomers.add(customer);
                    System.out.println("Customer ID: " + results.getInt("Customer_ID"));
                } else {
                    System.out.println("Cannot create customer because division is null for division id: " + divisionId);
                }
            }

            statement.close();
        } catch (SQLException var4) {
            System.out.println("Cannot retrieve Customers: " + var4.getMessage());
        }
    }

    //get all appointments from database and add to observable list for tableview
    public ObservableList<Appointment> getAllAppointments() {
        System.out.println("Retrieving Appointment Records");
        allAppointments.clear();

        try {
            Statement statement = ConnectDB.conn.createStatement();
            String query = "SELECT Appointment_ID, Contact_ID, Create_Date, Created_By, Customer_ID, Description, End, Last_Update, Last_Updated_By, Location, Start, Title, Type, User_ID FROM client_schedule.appointments";
            ResultSet results = statement.executeQuery(query);


            while (results.next()) {
                Timestamp createDate = results.getTimestamp("Create_Date");
                Timestamp endDate = results.getTimestamp("End");
                Timestamp lastUpdate = results.getTimestamp("Last_Update");
                Timestamp startDate = results.getTimestamp("Start");

                LocalDateTime createDateTime = createDate != null ? createDate.toLocalDateTime() : null;
                LocalDateTime endDateTime = endDate != null ? endDate.toLocalDateTime() : null;
                LocalDateTime lastUpdateDateTime = lastUpdate != null ? lastUpdate.toLocalDateTime() : null;
                LocalDateTime startDateTime = startDate != null ? startDate.toLocalDateTime() : null;

                Appointment appointment = new Appointment(
                        results.getInt("Appointment_ID"),
                        results.getInt("Contact_ID"),
                        createDateTime, // Use LocalDateTime or null
                        results.getString("Created_By"),
                        results.getInt("Customer_ID"),
                        results.getString("Description"),
                        endDateTime, // Use LocalDateTime or null
                        lastUpdateDateTime, // Use LocalDateTime or null
                        results.getString("Last_Updated_By"),
                        results.getString("Location"),
                        startDateTime, // Use LocalDateTime or null
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

    //report button
    @FXML
    private void reportAction(ActionEvent event) throws IOException {
        Parent reportParent = FXMLLoader.load(getClass().getResource("../views/Report.fxml"));
        Scene reportScene = new Scene(reportParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(reportScene);
        window.show();
    }

    //logout button
    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        // Load the login screen
        Parent loginParent = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        Scene loginScene = new Scene(loginParent);

        // Get the current stage and set the new scene
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();
    }

}
