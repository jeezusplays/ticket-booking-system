# README for Database Deployment

## Description
This folder contains the SQL script required to deploy the database schema used by the Ticket Booking application. The `deploy.sql` file will create all necessary tables and populate them with initial data.

## Prerequisites
- MySQL Server (version 8.0 or higher recommended).
- User with sufficient privileges to create databases, tables, and insert records.

## Deployment Instructions
1. Ensure that the MySQL server is installed and running on your system.
2. Open a terminal or command prompt.
3. Log into the MySQL command-line tool:
mysql -u [username] -p
Note: Replace `[username]` with your MySQL username. You will be prompted to enter your password.

4. Create the `ticket_booking` database by executing the following SQL command within the MySQL command-line tool:
```sql
CREATE DATABASE ticket_booking;

5. Exit the MySQL command-line tool by typing exit.
6. Navigate to the directory containing the deploy.sql file.
7. Deploy the database schema and initial data by executing the following command:
mysql -u [username] -p ticket_booking < deploy.sql
Note: Replace [username] with your MySQL username. After entering the command, you will be prompted to enter your password. The deploy.sql file will be imported into the ticket_booking database.

## Post-Deployment
1. After running the script, the ticket_booking database should be set up with all necessary tables and initial data.
2. You can now run the application that connects to this database.
If there are any issues, verify that your user has the correct privileges and that you are using the correct database name.
3. Confirm that the tables and initial data have been created by connecting to the `ticket_booking` database using a database management tool or the MySQL command-line tool and running some queries like `SHOW TABLES;` or `SELECT * FROM <table_name>;`.