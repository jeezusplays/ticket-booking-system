package service;

import data.Booking;

import java.sql.SQLException;

public class BookingService {
    private DatabaseService databaseService;

    public BookingService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public Booking createBooking(int eventID, int ticketOptionID, int ticketingOfficerID, int numOfTickets) throws SQLException {
        return this.databaseService.createBooking(eventID, ticketOptionID, AccountService.getCurrentUser().getID(), ticketingOfficerID, numOfTickets);
    }
}
