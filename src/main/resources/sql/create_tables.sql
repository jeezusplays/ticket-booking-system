-- Drop Tables
DROP TABLE IF EXISTS Refund;
DROP TABLE IF EXISTS Booking;
DROP TABLE IF EXISTS Ticket;
DROP TABLE IF EXISTS TicketOption;
DROP TABLE IF EXISTS AuthorisedOfficers;
DROP TABLE IF EXISTS Event;
DROP TABLE IF EXISTS TicketOfficer;
DROP TABLE IF EXISTS EventManager;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS User;


-- User Supertype
CREATE TABLE IF NOT EXISTS User (
    userID INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    name VARCHAR(30) NOT NULL,
    type ENUM('customer', 'eventmanager', 'ticketofficer') NOT NULL DEFAULT 'customer'
);


-- Customer Subtype
CREATE TABLE IF NOT EXISTS Customer (
    userID INT PRIMARY KEY,
    accountBalance DOUBLE NOT NULL,
    FOREIGN KEY (userID) REFERENCES User(userID)
);

-- EventManager Subtype
CREATE TABLE IF NOT EXISTS EventManager (
    userID INT PRIMARY KEY,
    FOREIGN KEY (userID) REFERENCES User(userID)
);

-- TicketOfficer Subtype
CREATE TABLE IF NOT EXISTS TicketOfficer (
    userID INT PRIMARY KEY,
    FOREIGN KEY (userID) REFERENCES User(userID)
);

-- Event Table
CREATE TABLE IF NOT EXISTS Event (
    eventID INT PRIMARY KEY AUTO_INCREMENT,
    managerID INT NOT NULL,
    basePrice DOUBLE NOT NULL,
    eventName VARCHAR(30) NOT NULL,
    venue VARCHAR(50) NOT NULL,
    startTime DATETIME NOT NULL,
    duration INT NOT NULL,
    ticketCancellationFee DOUBLE NOT NULL,
    status ENUM('draft', 'published', 'cancelled') NOT NULL DEFAULT 'draft',
    FOREIGN KEY (managerID) REFERENCES EventManager(userID)
);

-- AuthorisedOfficers Associative Table
CREATE TABLE IF NOT EXISTS AuthorisedOfficers (
    ticketOfficerID INT NOT NULL,
    eventID INT NOT NULL,
    timeStamp DATETIME NOT NULL,
    PRIMARY KEY (ticketOfficerID, eventID),
    FOREIGN KEY (ticketOfficerID) REFERENCES TicketOfficer(userID),
    FOREIGN KEY (eventID) REFERENCES Event(eventID)
);

-- TicketOption Table
CREATE TABLE IF NOT EXISTS TicketOption (
    ticketOptionID INT PRIMARY KEY AUTO_INCREMENT,
    eventID INT NOT NULL,
    name VARCHAR(30) NOT NULL,
    priceMultiplier DOUBLE NOT NULL,
    totalAvailable DOUBLE NOT NULL, 
    FOREIGN KEY (eventID) REFERENCES Event(eventID)
);

-- Ticket Table
CREATE TABLE IF NOT EXISTS Ticket (
    ticketID INT PRIMARY KEY AUTO_INCREMENT,
    bookingID INT NOT NULL,
    isGuest TINYINT NOT NULL,
    attended TINYINT NOT NULL,
    FOREIGN KEY (bookingID) REFERENCES Booking(bookingID)
);

-- Booking Table
CREATE TABLE IF NOT EXISTS Booking (
    bookingID INT PRIMARY KEY AUTO_INCREMENT,
    eventID INT NOT NULL,
    ticketOptionID INT NOT NULL,
    customerID INT NOT NULL,
    ticketOfficerID INT,
    bookedTime DATETIME NOT NULL,
    FOREIGN KEY (eventID) REFERENCES Event(eventID),
    FOREIGN KEY (ticketOptionID) REFERENCES TicketOption(ticketOptionID),
    FOREIGN KEY (customerID) REFERENCES Customer(userID),
    FOREIGN KEY (ticketOfficerID) REFERENCES TicketOfficer(userID)
);

-- Refund Table
CREATE TABLE IF NOT EXISTS Refund (
    refundID INT PRIMARY KEY AUTO_INCREMENT,
    bookingID INT NOT NULL,
    refundDate DATETIME NOT NULL,
    refundStatus VARCHAR(30) NOT NULL,
    FOREIGN KEY (bookingID) REFERENCES Booking(bookingID)
);

