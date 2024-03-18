-- Insert data into User table
INSERT INTO User (email, password, name, type)
VALUES ('customer1@example.com', 'password1', 'Customer 1', 'customer'),
    ('eventmanager1@example.com', 'password2', 'Event Manager 1', 'eventmanager'),
    ('ticketofficer1@example.com', 'password3', 'Ticket Officer 1', 'ticketofficer');

-- Insert data into Customer table
INSERT INTO Customer (userID, accountBalance)
VALUES (1, 1000.00),
    (2, 500.00);

-- Insert data into EventManager table
INSERT INTO EventManager (userID)
VALUES (2);

-- Insert data into TicketOfficer table
INSERT INTO TicketOfficer (userID)
VALUES (3);

-- Insert data into Event table
INSERT INTO Event (managerID, basePrice, eventName, venue, startTime, duration, ticketCancellationFee, status)
VALUES (2, 50.00, 'Concert 1', 'Venue 1', '2022-01-01 18:00:00', 120, 10.00, 'published'),
    (2, 30.00, 'Concert 2', 'Venue 2', '2022-02-01 19:00:00', 90, 5.00, 'draft');

-- Insert data into AuthorisedOfficers table
INSERT INTO AuthorisedOfficers (ticketOfficerID, eventID, timeStamp)
VALUES (3, 1, '2022-01-01 12:00:00'),
    (3, 2, '2022-02-01 10:00:00');

-- Insert data into TicketOption table
INSERT INTO TicketOption (eventID, name, priceMultiplier, totalAvailable)
VALUES (1, 'General Admission', 1.0, 100),
    (1, 'VIP', 1.5, 50),
    (2, 'Standard', 1.0, 200);

-- Insert data into Ticket table
INSERT INTO Ticket (bookingID, isGuest, attended)
VALUES (1, 0, 0),
    (2, 1, 1);

-- Insert data into Booking table
INSERT INTO Booking (eventID, ticketOptionID, customerID, ticketOfficerID, bookedTime)
VALUES (1, 1, 1, NULL, '2022-01-01 10:00:00'),
    (2, 2, 2, 3, '2022-02-01 09:00:00');

-- Insert data into Refund table
INSERT INTO Refund (bookingID, refundDate, refundStatus)
VALUES (1, '2022-01-02 10:00:00', 'pending'),
    (2, '2022-02-02 09:00:00', 'approved');