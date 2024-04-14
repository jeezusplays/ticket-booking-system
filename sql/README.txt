# README for Database Deployment

## Description
This folder contains the SQL script required to deploy the database schema used by the Ticket Booking application. The `deploy.sql` file will create all necessary tables and populate them with initial data.

## Prerequisites
- MySQL Server (version 8.0 or higher recommended).
- User with sufficient privileges to create databases, tables, and insert records.

## Deployment Instructions
1. Ensure that the MySQL server is installed and running on your system.
2. Open a terminal or command prompt.
3. Navigate to the directory containing the `deploy.sql` file.
4. Execute the script using the following command:
mysql -u [username] -p [database_name] < deploy.sql
Note: Replace `[username]` with your MySQL username and `[database_name]` with the name of the database you wish to use. After entering the command, you will be prompted to enter your password. The `deploy.sql` file will be imported into the specified database.

## Post-Deployment
- After running the script, the database should be set up with all necessary tables and initial data.
- You can now run the application that connects to this database.
- If there are any issues, verify that your user has the correct privileges and that you are using the correct database name.
