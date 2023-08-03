Title: Appointment Scheduling System
Purpose: This application is designed to manage appointments and customers. It supports user
authentication, appointment scheduling, modification, and reporting, with data stored in a
MySQL database.

Author: Katherine Caudill
Contact Information: kcaud33@wgu.edu
Student Application Version: 1
Date: 08/01/2023

============================================================================================================================================
IDE and Other Tools Information:
IntelliJ IDEA 2023.2 (Community Edition)
Build #IC-232.8660.185, built on July 26, 2023
Runtime version: 17.0.7+7-b1000.6 amd64
Java Version: 20.0.1, OpenJDK Runtime Environment
JavaFX Version: 20.0.1
JavaFX Scene Builder 20.0.0 (Version 20.0.0, Date: 2023-05-08 14:07:40)

============================================================================================================================================
Importing the Database Schema in MySQL Workbench

    Open MySQL Workbench: Launch MySQL Workbench on your computer.

    Connect to MySQL Server Instance:
        Click on the + icon next to "MySQL Connections" to create a new connection.
        Enter the connection name, host name (usually localhost), port (usually 3306), and the username and password for your MySQL server.
        Click Test Connection to ensure everything is set up correctly.
        Click OK to save the connection, and then double-click the connection name to connect to your MySQL server.

    Create a New Schema (if necessary):
        Right-click in the "SCHEMAS" panel on the left, and select Create Schema.
        Enter the schema name, and click Apply.
        Review the SQL command, and click Apply again.
        Click Finish.

    Import the Database Schema:
        Go to File > Open SQL Script.
        Navigate to the location of your .sql file containing the database schema, and click Open.
        Select the appropriate schema from the dropdown menu in the toolbar above the SQL editor (if it doesn't automatically select the one you just created).
        Click the "lightning bolt" icon (or Query > Execute (All or Selection)) to run the script.
        Check the "Output" panel at the bottom to ensure that the script executed without errors.

    Verify the Schema:
        In the "SCHEMAS" panel on the left, click the refresh button (or right-click and choose Refresh All).
        Expand your schema to view the tables and other objects to ensure everything has been imported correctly.

    Update the ConnectDB Class in IntelliJ IDEA: Make sure the databaseName in the ConnectDB class matches the name of the schema you just imported.

By following these steps, you will have successfully imported the database schema into MySQL Workbench, and your program should be able to connect and interact with it.

Please note that the specific steps and options may vary slightly depending on the version of MySQL Workbench you are using, so always refer to the documentation or help files for the exact details.

============================================================================================================================================
Connecting Database to the Program

    Download the MySQL Connector/J: This driver is a JDBC type 4 driver, also known as a direct-to-database pure Java driver. It converts JDBC calls into the network protocol used by DBMS directly. Download it from the official MySQL website and remember the version you're using (as of your readme, it's mysql-connector-java-8.1.23).

    Add the MySQL Connector/J to your Project Libraries in IntelliJ IDEA:
        Go to File > Project Structure.
        Select Modules on the left, and go to the Dependencies tab.
        Click the + button and select JARs or directories.
        Navigate to the location where you saved the MySQL Connector/J .jar file, select it, and click OK.
        Click Apply and then OK.

    Configure the ConnectDB Class: The ConnectDB class in your project is responsible for connecting your application to the MySQL database. You need to ensure that the connection details in this class are correctly set up.
        The DB_URL should be in the format jdbc:mysql://hostname:port/databasename. Replace hostname with the name of your MySQL server (e.g., localhost if your MySQL server is running on your local machine), port with the port number on which your MySQL server is listening (default is 3306), and databasename with the name of your database.
        username and password should be the username and password for your MySQL server.

    Run the Program: Build and run the 'Main' class to launch the application. Your application should now be able to connect to the MySQL database and perform database operations.

Please note that the provided ConnectDB class assumes that you are using a MySQL database running on your local machine with the default MySQL port (3306). If your MySQL server is running on a different machine, or if it's using a different port, you will need to modify the DB_URL accordingly.

Also, remember to handle any potential SQLException that might be thrown when making the connection or performing database operations. This is already done in the ConnectDB class, but make sure similar exception handling is done anywhere you're interacting with the database.

Remember to replace the placeholders (databaseName, username, password, and driver) with your actual database details.

============================================================================================================================================
Directions for Running the Program:
1. Open the project in IntelliJ IDEA.
2. Ensure that the JDK and JavaFX versions are correctly set up in the project settings.
3. Configure the project SDK to use Java 20.
4. Add the JavaFX library to the project.
5. Set the VM options for JavaFX.
4. Add the MySQL Connector driver to the project libraries.
5. Configure the database connection details in the ConnectDB class.
6. Build and run the 'Main' class to launch the application.
7. Log in using a valid username and password from the database.

============================================================================================================================================
Additional Report Description (A3f):
The additional report created as part of this project is designed to retrieve and display contact
information, including phone numbers and email addresses, for all the contacts in the system.
This report can be accessed within the application's reporting section and provides an easy way
to find essential contact details. By running this report, users can quickly view the contact
information, making it a valuable tool for customer relationship management and
communication.

============================================================================================================================================
MySQL Connector Driver Version: mysql-connector-java-8.1.23
Note: Please ensure that all the dependencies, such as JavaFX and MySQL Connector, are
correctly configured for your system and match the versions mentioned above.
