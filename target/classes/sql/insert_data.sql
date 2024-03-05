-- Insert Users
INSERT INTO User (userID, username, password, role) VALUES
('U001', 'admin', 'password', 'EventManager'),
('U002', 'officer1', 'password', 'TicketOfficer'),
('U003', 'customer1', 'password', 'Customer');

-- Insert Customers
INSERT INTO Customer (userID, accountBalance) VALUES
('U003', 200.00);

-- Insert EventManagers
INSERT INTO EventManager (userID) VALUES
('U001');

-- Insert TicketOfficers
INSERT INTO TicketOfficer (userID) VALUES
('U002');

-- Insert Events
-- INSERT INTO Event (eventID, managerID, basePrice, eventName, venue, startTime, duration, ticketCancellationFee, isCancelled) VALUES
-- ('E001', 'U001', 50.00, 'Concert', 'Concert Hall', '2023-12-25 19:00:00', 120, 5.00, 0);

-- Insert AuthorisedOfficers
-- INSERT INTO AuthorisedOfficers (ticketOfficerID, eventID, timeStamp) VALUES
-- ('U002', 'E001', '2023-12-01 00:00:00');

-- Insert TicketOptions
-- INSERT INTO TicketOption (ticketOptionID, eventID, name, priceMultiplier, totalAvailable) VALUES
-- ('TO001', 'E001', 'VIP', 2, 50),
-- ('TO002', 'E001', 'Standard', 1, 100);

-- Insert Tickets
-- INSERT INTO Ticket (ticketID, bookingID, isGuest, attended) VALUES
-- ('T001', 'B001', 0, 0);

-- Insert Bookings
-- INSERT INTO Booking (bookingID, eventID, ticketOptionID, customerID, ticketOfficerID, bookedTime) VALUES
-- ('B001', 'E001', 'TO001', 'U003', 'U002', '2023-12-24 10:00:00');


-- Insert Refunds
-- INSERT INTO Refund (bookingID, refundDate, refundStatus) VALUES

