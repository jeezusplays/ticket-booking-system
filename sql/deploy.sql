-- SQL Deployment Script for Ticket Booking Application

-- Create User Supertype
CREATE TABLE IF NOT EXISTS User (
    userID INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    name VARCHAR(30) NOT NULL,
    type VARCHAR(30) NOT NULL
);

-- Create Customer Subtype
CREATE TABLE IF NOT EXISTS Customer (
    userID INT PRIMARY KEY,
    accountBalance DOUBLE NOT NULL,
    FOREIGN KEY (userID) REFERENCES User(userID)
);

-- Create EventManager Subtype
CREATE TABLE IF NOT EXISTS EventManager (
    userID INT PRIMARY KEY,
    FOREIGN KEY (userID) REFERENCES User(userID)
);

-- Create TicketOfficer Subtype
CREATE TABLE IF NOT EXISTS TicketingOfficer (
    userID INT PRIMARY KEY,
    FOREIGN KEY (userID) REFERENCES User(userID)
);

-- Create Event
CREATE TABLE IF NOT EXISTS Event (
    eventID INT PRIMARY KEY AUTO_INCREMENT,
    eventManagerID INT NOT NULL,
    basePrice DOUBLE NOT NULL,
    eventName VARCHAR(30) NOT NULL,
    eventDesc VARCHAR (200) NOT NULL,
    venue VARCHAR(50) NOT NULL,
    startTime DATETIME NOT NULL,
    duration INT NOT NULL,
    revenue DOUBLE NOT NULL,
    currSlots INT NOT NULL,
    totalSlots INT NOT NULL,
    ticketCancellationFee DOUBLE NOT NULL,
    isCancelled TINYINT NOT NULL,
    FOREIGN KEY (eventmanagerID) REFERENCES EventManager(userID)
);

-- Create AuthorisedOfficers Associative
CREATE TABLE IF NOT EXISTS AuthorisedOfficers (
    ticketingOfficerID INT NOT NULL,
    eventID INT NOT NULL,
    timeStamp DATETIME NOT NULL,
    PRIMARY KEY (ticketingOfficerID, eventID),
    FOREIGN KEY (ticketingOfficerID) REFERENCES TicketingOfficer(userID),
    FOREIGN KEY (eventID) REFERENCES Event(eventID)
);

-- Create TicketOption
CREATE TABLE IF NOT EXISTS TicketOption (
    ticketOptionID INT PRIMARY KEY AUTO_INCREMENT,
    eventID INT NOT NULL,
    optionName VARCHAR(30) NOT NULL,
    priceMultiplier DOUBLE NOT NULL,
    totalAvailable INT NOT NULL,
    FOREIGN KEY (eventID) REFERENCES Event(eventID)
);

-- Create Ticket
CREATE TABLE IF NOT EXISTS Ticket (
    ticketID INT PRIMARY KEY AUTO_INCREMENT,
    bookingID INT NOT NULL,
    isGuest TINYINT NOT NULL,
    attended TINYINT NOT NULL,
    FOREIGN KEY (bookingID) REFERENCES Booking(bookingID)
);

-- Create Booking
CREATE TABLE IF NOT EXISTS Booking (
    bookingID INT PRIMARY KEY AUTO_INCREMENT,
    eventID INT NOT NULL,
    ticketOptionID INT NOT NULL,
    customerID INT NOT NULL,
    ticketingOfficerID INT NOT NULL,
    numOfTickets INT NOT NULL,
    amountPaid DOUBLE NOT NULL,
    bookedTime DATETIME NOT NULL,
    bookingStatus VARCHAR(30) NOT NULL,
    FOREIGN KEY (eventID) REFERENCES Event(eventID),
    FOREIGN KEY (ticketOptionID) REFERENCES TicketOption(ticketOptionID),
    FOREIGN KEY (customerID) REFERENCES Customer(userID),
    FOREIGN KEY (ticketingOfficerID) REFERENCES TicketingOfficer(userID)
);

-- Create Refund
CREATE TABLE IF NOT EXISTS Refund (
    refundID INT PRIMARY KEY AUTO_INCREMENT,
    bookingID INT NOT NULL,
    refundDate DATETIME NOT NULL,
    refundStatus VARCHAR(30) NOT NULL,
    FOREIGN KEY (bookingID) REFERENCES Booking(bookingID)
);

-- Insert initial data records...

-- Remember to replace the following with your actual initial data.
-- Insert Users
INSERT INTO User (email, password, name, type) VALUES
 ('customer@gmail.com', 'password', 'Cust', 'Customer'),
 ('admin@gmail.com', 'password', 'Admin', 'EventManager'),
 ('officer@gmail.com', 'password', 'T_Officer','TicketingOfficer');

-- Insert Customer
INSERT INTO Customer (userID, accountBalance) VALUES (1, 1000.00);

-- Insert EventManager
INSERT INTO EventManager (userID) VALUES (2);

-- Insert TicketingOfficer
INSERT INTO TicketingOfficer (userID) VALUES (3);

-- Insert Events
INSERT INTO Event (eventManagerID, basePrice, eventName, eventDesc, venue, startTime, duration, revenue, currSlots, totalSlots, ticketCancellationFee, isCancelled) VALUES
    (2, 50.00, 'Tech Conference 2024', 'An annual conference on the latest tech trends.', 'Convention Center', '2024-07-15 09:00:00', 480, 0.00, 200, 200, 10.00, 0),
    (2, 75.00, 'Live Music Festival', 'A weekend of live music from top artists around the world.', 'Open Air Park', '2024-08-20 15:00:00', 240, 0.00, 500, 500, 15.00, 0),
    (2, 40.00, 'Art & Wine Fair', 'Explore fine arts and taste exquisite wines.', 'Downtown Gallery', '2024-09-10 11:00:00', 120, 0.00, 150, 150, 5.00, 0),
    (2, 30.00, 'November Concert', 'For the purpose of more than 6 months.', 'Kallang Stadium', '2024-11-10 11:00:00', 180, 0.00, 200, 200, 45.00,0),
    (2, 30.00, 'April Concert', 'For the purpose of less than 24 hours.', 'Kallang Stadium', '2024-04-14 18:00:00', 75, 0.00, 200, 200, 45.00,0);


-- Insert Ticket Options for Tech Conference 2024
INSERT INTO TicketOption (eventID, optionName, priceMultiplier, totalAvailable) VALUES
    (1, 'CAT1', 2.00, 50),
    (1, 'CAT2', 1.50, 50),
    (1, 'CAT3', 1.00, 100);

-- Insert Ticket Options for Live Music Festival
INSERT INTO TicketOption (eventID, optionName, priceMultiplier, totalAvailable) VALUES
    (2, 'CAT1', 2.00, 50),
    (2, 'CAT2', 1.75, 50),
    (2, 'CAT3', 1.50, 100),
    (2, 'CAT4', 1.25, 150),
    (2, 'CAT5', 1.00, 150);

-- Insert Ticket Options for Art & Wine Fair
INSERT INTO TicketOption (eventID, optionName, priceMultiplier, totalAvailable) VALUES
    (3, 'Standard', 1.00, 100),
    (3, 'VIP', 1.50, 50);

-- Insert Ticket Options for November Concert
INSERT INTO TicketOption (eventID, optionName, priceMultiplier, totalAvailable) VALUES
    (4, 'CAT1', 2.00, 50),
    (4, 'CAT2', 1.50, 50),
    (4, 'CAT3', 1.00, 100);

-- Insert Ticket Options for April Concert
INSERT INTO TicketOption (eventID, optionName, priceMultiplier, totalAvailable) VALUES
    (5, 'Standard', 2.00, 50),
    (5, 'VIP', 1.50, 25);

-- End of deploy.sql
