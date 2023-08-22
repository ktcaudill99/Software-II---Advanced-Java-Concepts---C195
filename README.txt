Certainly! Here's the updated document, incorporating the provided details:

Title: Appointment Scheduling System

Purpose: This application is designed to manage appointments and customers. It supports user authentication, appointment scheduling, modification, and reporting, with data stored in a MySQL database.

Author: Katherine Caudill
Contact Information: kcaud33@wgu.edu
Student Application Version: 1
Date: 08/01/2023

IDE and Other Tools Information:

IntelliJ IDEA:

Version: 2021.1.3 (Community Edition)
Build #IC-211.7628.21, built on June 30, 2021
Runtime version: 11.0.11+9-b1341.60 amd64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
Windows 10 10.0
GC: G1 Young Generation, G1 Old Generation
Memory: 768M
Cores: 4
Kotlin: 211-1.4.32-release-IJ7628.19
JavaFX Scene Builder:

Product Version: 17.0.0
Build Information: Version 17.0.0, Date: 2021-09-29 13:41:58
JavaFX Version: 17
Java Version: 17, OpenJDK Runtime Environment
Directions for Running the Program:

Open the project in IntelliJ IDEA.
Ensure that the JDK and JavaFX versions are correctly set up in the project settings.
Configure the project SDK to use Java 17.
Add the JavaFX library to the project.
Set the VM options for JavaFX.
Add the MySQL Connector driver to the project libraries.
In IntelliJ, navigate to: File > Project Structure > Libraries and ensure that the mysql-connector-java-8.0.25.jar class is present.
Configure the database connection details in the ConnectDB class.
Build and run the 'Main' class to launch the application.
Log in using a valid username and password from the database.
Additional Report Description (A3f):
The additional report created as part of this project is designed to retrieve and display contact information, including phone numbers and email addresses, for all the contacts in the system. This report can be accessed within the application's reporting section and provides an easy way to find essential contact details. By running this report, users can quickly view the contact information, making it a valuable tool for customer relationship management and communication.

MySQL Connector Driver Version: mysql-connector-java-8.0.25
Note: Please ensure that all the dependencies, such as JavaFX and MySQL Connector, are correctly configured for your system and match the versions mentioned above.

in intellj
Filel > Project structure > Libraries 
then makesure that the mysql-connector-java-8.0.25.jar class is there