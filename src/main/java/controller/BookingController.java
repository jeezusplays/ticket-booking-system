package controller;

import java.util.List;

public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Get all bookings for a user method
    public List<Booking> getBookings(User user) {
        return bookingService.getBookings(user);
    }

    // Create a new booking for a user method
    public Booking createBooking(User user, int numTickets, String eventID, int numGuests) {
        return bookingService.createBooking(user, numTickets, eventID, numGuests);
    }

    // Create a new booking for a user by a ticket officer method
    public Booking createBookingFor(TicketOfficer ticketOfficer, int numTickets, String eventID, int numGuests, String userID) {
        return bookingService.createBookingFor(ticketOfficer, numTickets, eventID, numGuests, userID);
    }

    // Cancel a booking method
    public Refund cancelBooking(User user, String bookingID) {
        return bookingService.cancelBooking(user, bookingID);
    }
}
