-- User Supertype
CREATE TABLE IF NOT EXISTS User (
    userID INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    name VARCHAR(30) NOT NULL,
    type VARCHAR(30) NOT NULL
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
    eventID VARCHAR(10) PRIMARY KEY,
    managerID VARCHAR(10),
    basePrice DOUBLE NOT NULL,
    eventName VARCHAR(30) NOT NULL,
    venue VARCHAR(50) NOT NULL,
    startTime DATETIME NOT NULL,
    duration INT NOT NULL,
    ticketCancellationFee DOUBLE NOT NULL,
    isCancelled TINYINT NOT NULL,
    FOREIGN KEY (managerID) REFERENCES EventManager(userID)
);

-- AuthorisedOfficers Associative Table
CREATE TABLE IF NOT EXISTS AuthorisedOfficers (
    ticketOfficerID VARCHAR(10),
    eventID VARCHAR(10),
    timeStamp DATETIME NOT NULL,
    PRIMARY KEY (ticketOfficerID, eventID),
    FOREIGN KEY (ticketOfficerID) REFERENCES TicketOfficer(userID),
    FOREIGN KEY (eventID) REFERENCES Event(eventID)
);

-- TicketOption Table
CREATE TABLE IF NOT EXISTS TicketOption (
    ticketOptionID VARCHAR(10) PRIMARY KEY,
    eventID VARCHAR(10),
    name VARCHAR(30) NOT NULL,
    priceMultiplier INT NOT NULL,
    totalAvailable INT NOT NULL,
    FOREIGN KEY (eventID) REFERENCES Event(eventID)
);

-- Ticket Table
CREATE TABLE IF NOT EXISTS Ticket (
    ticketID VARCHAR(10) PRIMARY KEY,
    bookingID VARCHAR(10),
    isGuest TINYINT NOT NULL,
    attended TINYINT NOT NULL,
    FOREIGN KEY (bookingID) REFERENCES Booking(bookingID)
);

-- Booking Table
CREATE TABLE IF NOT EXISTS Booking (
    bookingID VARCHAR(10) PRIMARY KEY,
    eventID VARCHAR(10),
    ticketOptionID VARCHAR(10),
    customerID VARCHAR(10),
    ticketOfficerID VARCHAR(10),
    bookedTime DATETIME NOT NULL,
    FOREIGN KEY (eventID) REFERENCES Event(eventID),
    FOREIGN KEY (ticketOptionID) REFERENCES TicketOption(ticketOptionID),
    FOREIGN KEY (customerID) REFERENCES Customer(userID),
    FOREIGN KEY (ticketOfficerID) REFERENCES TicketOfficer(userID)
);

-- Refund Table
CREATE TABLE IF NOT EXISTS Refund (
    refundID INT PRIMARY KEY AUTO_INCREMENT,
    bookingID VARCHAR(10),
    refundDate DATETIME NOT NULL,
    refundStatus VARCHAR(30) NOT NULL,
    FOREIGN KEY (bookingID) REFERENCES Booking(bookingID)
);