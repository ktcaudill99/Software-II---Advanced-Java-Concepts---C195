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
1.  Open the project in IntelliJ IDEA.
2.  Ensure that the JDK and JavaFX versions are correctly set up in the project settings.
3.  Select "IntelliJ IDEA" on the menu bar.
4.  Select "Preferences..."
5.  Select "Path Variables" on the left.
6.  Set variable PATH_TO_FX to the directory of your JavaFX library
7.  (e.g. /Library/javafx-sdk-20.0.1/lib).
8.  Configure the project SDK to use Java 20.
9.  Add the JavaFX library to the project.
10. Set the VM options for JavaFX.
11. Add the MySQL Connector driver to the project libraries.
12. Configure the database connection details in the ConnectDB class.
13. Build and run the 'Main' class to launch the application.
14. Log in using a valid username and password from the database.


(2) Select "IntelliJ IDEA" on the menu bar.
(3) Select "Preferences..."
(4) Select "Path Variables" on the left.
(5) Set variable PATH_TO_FX to the directory of your JavaFX library
    (e.g. /Library/javafx-sdk-20.0.1/lib).
(6) Select "Run" and then "Run 'Main'" on the menu bar."


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

============================================================================================================================================
# c195
QAM2 — QAM2 Task 1: Java Application Development

Software II - Advanced Java Concepts — C195

PRFA — QAM2

Competencies

4025.01.05 : Database and File Server Applications

The graduate produces database and file server applications using advanced Java programming language constructs to meet business requirements.

4025.01.06 : Lambda

The graduate incorporates lambda expressions in application development to meet business requirements more efficiently.

4025.01.07 : Collections

The graduate incorporates streams and filters in application development to manipulate data more efficiently.

4025.01.08 : Localization API and Date/Time API

The graduate applies the localization API and date/time API in application development to support end-users in various geographical regions.

4025.01.09 : Advanced Exception Control

The graduate incorporates advanced exception control mechanisms in application development for improving user experience and application stability.
Introduction

Throughout your career in software design and development, you will be asked to create applications with various features and criteria based on a variety of business requirements. For this assessment, you will create your own GUI-based Java application with requirements that mirror those you will likely encounter in a real-world job assignment. 


The skills you will showcase in this assessment are also directly relevant to technical interview questions for future employment. This application may become a portfolio piece for you to show to future employers.


Several attachments and links have been included to help you complete this task. The attached “Database ERD” shows the entity relationship diagram (ERD) for this database, which you can reference as you create your application.


For this task, you will use the Performance Assessment Lab Area accessed by the Performance Assessment Lab Area in the web link to access the virtual lab environment to complete this task. The preferred integrated development environment (IDE) for this assignment is IntelliJ IDEA (Community Edition). If you choose to use another IDE, you must export your project into NetBeans 11.1 or later, or IntelliJ format, or your submission will be returned. This assessment also requires the following software: the latest long term support (LTS) version of JDK, JavaFX SDK, and Scene Builder which are also available the Performance Assessment Lab Area.

 

Your submission should include a zip file with all the necessary code files to compile, support, and run your application. Your submission should also include an index.html file with reflective Javadoc comments in the .java files. The zip file submission must keep the project file and folder structure intact for the IDE. 


In NetBeans, zip your file by going to File>Export Project>To ZIP and click Export. In IntelliJ Idea, go to File>Export to Zip File and click OK. If you try to zip your project files with an external program, it will include the build files and make the zip files too large for submission. 


Note: You may receive an error message upon submitting your files because the automated plagiarism detectors at WGU will not be able to access a zipped file, but the evaluation team will run their checks manually when evaluating your submission.
Scenario
You are working for a software company that has been contracted to develop a GUI-based scheduling desktop application. The contract is with a global consulting organization that conducts business in multiple languages and has main offices in Phoenix, Arizona; White Plains, New York; Montreal, Canada; and London, England. The consulting organization has provided a MySQL database that the application must pull data from. The database is used for other systems, so its structure cannot be modified.

The organization outlined specific business requirements that must be met as part of the application. From these requirements, a system analyst at your company created solution statements for you to implement in developing the application. These statements are listed in the requirements section.

Your company acquires Country and First-Level-Division data from a third party that is updated once per year. These tables are prepopulated with read-only data. Please use the attachment “Locale Codes for Region and Language” to review division data. Your company also supplies a list of contacts, which are prepopulated in the Contacts table; however, administrative functions such as adding users are beyond the scope of the application and done by your company’s IT support staff. Your application should be organized logically using one or more design patterns and generously commented using Javadoc so your code can be read and maintained by other programmers.
Requirements

Your submission must be your original work. No more than a combined total of 30% of the submission and no more than a 10% match to any one individual source can be directly quoted or closely paraphrased from sources, even if cited correctly. The originality report that is provided when you submit your task can be used as a guide.


You must use the rubric to direct the creation of your submission because it provides detailed criteria that will be used to evaluate your work. Each requirement below may be evaluated by more than one rubric aspect. The rubric aspect titles may contain hyperlinks to relevant portions of the course.


Tasks may not be submitted as cloud links, such as links to Google Docs, Google Slides, OneDrive, etc., unless specified in the task requirements. All other submissions must be file types that are uploaded and submitted as attachments (e.g., .docx, .pdf, .ppt).


A.  Create a GUI-based application for the company in the scenario. Regarding your file submission—the use of non-Java API libraries are not allowed with the exception of JavaFX SDK and MySQL JDBC Driver. If you are using the NetBeans IDE, the custom library for your JavaFX .jar files in your IDE must be named JavaFX.


Note: If you are using IntelliJ IDEA, the folder where the JavaFX SDK resides will be used as the library name as shown in the “JavaFX SDK with IntelliJ IDEA webinar.


1.  Create a log-in form with the following capabilities:

•  accepts username and password and provides an appropriate error message

•  determines the user’s location (i.e., ZoneId) and displays it in a label on the log-in form

•  displays the log-in form in English or French based on the user’s computer language setting to translate all the text, labels, buttons, and errors on the form

•  automatically translates error control messages into English or French based on the user’s computer language setting


Note: Some operating systems require a reboot when changing the language settings.


2.  Write code that provides the following customer record functionalities:

•  Customer records and appointments can be added, updated, and deleted.

-  When deleting a customer record, all of the customer’s appointments must be deleted first, due to foreign key constraints.

•  When adding and updating a customer, text fields are used to collect the following data: customer name, address, postal code, and phone number.

-  Customer IDs are auto-generated, and first-level division (i.e., states, provinces) and country data are collected using separate combo boxes.


Note: The address text field should not include first-level division and country data. Please use the following examples to format addresses:

•  U.S. address: 123 ABC Street, White Plains

•  Canadian address: 123 ABC Street, Newmarket

•  UK address: 123 ABC Street, Greenwich, London


-  When updating a customer, the customer data autopopulates in the form.


•  Country and first-level division data is prepopulated in separate combo boxes or lists in the user interface for the user to choose. The first-level list should be filtered by the user’s selection of a country (e.g., when choosing U.S., filter so it only shows states).

•  All of the original customer information is displayed on the update form.

-  Customer_ID must be disabled.

•  All of the fields can be updated except for Customer_ID.

•  Customer data is displayed using a TableView, including first-level division data. A list of all the customers and their information may be viewed in a TableView, and updates of the data can be performed in text fields on the form.

•  When a customer record is deleted, a custom message should display in the user interface.


3.  Add scheduling functionalities to the GUI-based application by doing the following:

a.  Write code that enables the user to add, update, and delete appointments. The code should also include the following functionalities:

•  A contact name is assigned to an appointment using a drop-down menu or combo box.

•  A custom message is displayed in the user interface with the Appointment_ID and type of appointment canceled.

•  The Appointment_ID is auto-generated and disabled throughout the application.

•  When adding and updating an appointment, record the following data: Appointment_ID, title, description, location, contact, type, start date and time, end date and time, Customer_ID, and User_ID.

•  All of the original appointment information is displayed on the update form in local time zone.

•  All of the appointment fields can be updated except Appointment_ID, which must be disabled.


b.  Write code that enables the user to view appointment schedules by month and week using a TableView and allows the user to choose between these two options using tabs or radio buttons for filtering appointments. Please include each of the following requirements as columns:

•  Appointment_ID

•  Title

•  Description

•  Location

•  Contact

•  Type

•  Start Date and Time

•  End Date and Time

•  Customer_ID

•  User_ID


c.  Write code that enables the user to adjust appointment times. While the appointment times should be stored in Coordinated Universal Time (UTC), they should be automatically and consistently updated according to the local time zone set on the user’s computer wherever appointments are displayed in the application.


Note: There are up to three time zones in effect. Coordinated Universal Time (UTC) is used for storing the time in the database, the user’s local time is used for display purposes, and Eastern Standard Time (EST) is used for the company’s office hours. Local time will be checked against EST business hours before they are stored in the database as UTC.


d.  Write code to implement input validation and logical error checks to prevent each of the following changes when adding or updating information; display a custom message specific for each error check in the user interface:

•  scheduling an appointment outside of business hours defined as 8:00 a.m. to 10:00 p.m. EST, including weekends

•  scheduling overlapping appointments for customers

•  entering an incorrect username and password


e.  Write code to provide an alert when there is an appointment within 15 minutes of the user’s log-in. A custom message should be displayed in the user interface and include the appointment ID, date, and time. If the user does not have any appointments within 15 minutes of logging in, display a custom message in the user interface indicating there are no upcoming appointments.


Note: Since evaluation may be testing your application outside of business hours, your alerts must be robust enough to trigger an appointment within 15 minutes of the local time set on the user’s computer, which may or may not be EST.


f.  Write code that generates accurate information in each of the following reports and will display the reports in the user interface:


Note: You do not need to save and print the reports to a file or provide a screenshot.


•  the total number of customer appointments by type and month

•  a main for each contact in your organization that includes appointment ID, title, type and description, start date and time, end date and time, and customer ID

•  an additional report of your choice that is different from the two other required reports in this prompt and from the user log-in date and time stamp that will be tracked in part C


B.  Write at least two different lambda expressions to improve your code.


C.  Write code that provides the ability to track user activity by recording all user log-in attempts, dates, and time stamps and whether each attempt was successful in a file named login_activity.txt. Append each new record to the existing file, and save to the root folder of the application.


D.  Provide descriptive Javadoc comments for at least 70 percent of the classes and their members throughout the code, and create an index.html file of your comments to include with your submission based on Oracle’s guidelines for the Javadoc tool best practices. Your comments should include a justification for each lambda expression in the method where it is used.


Note: The comments on the lambda need to be located in the comments describing the method where it is located for it to export properly.


E.  Create a README.txt file that includes the following information:

•  title and purpose of the application

•  author, contact information, student application version, and date

•  IDE including version number (e.g., IntelliJ Community 2020.01), full JDK of version used (e.g., Java SE 17.0.1), and JavaFX version compatible with JDK version (e.g. JavaFX-SDK-17.0.1)

•  directions for how to run the program

•  a description of the additional report of your choice you ran in part A3f

•  the MySQL Connector driver version number, including the update number (e.g., mysql-connector-java-8.1.23)


F.  Demonstrate professional communication in the content and presentation of your submission.

