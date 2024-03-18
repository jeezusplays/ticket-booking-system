package service;

import user.User;
import user.TicketingOfficer;
import user.EventManager;

import data.Booking;
import data.Event;
import data.Refund;

import java.util.List;

public class BookingService {
    private DatabaseService databaseService;
    private RefundService refundService;

    public BookingService(DatabaseService databaseService, RefundService refundService) {
        this.databaseService = databaseService;
        this.refundService = refundService;
    }

    // Get all bookings for a user method
    public List<Booking> getBookings(User user) {
        return databaseService.getBookings(user);
    }

    // Get all bookings for an event method
    public List<Booking> getBookingsByEvent(Event event) {
        return databaseService.getBookingsByEvent(event);
    }

    // Create a new booking for a user method
    public Booking createBooking(User user, int numTickets, String eventID, int numGuests) {
        return databaseService.createBooking(user, numTickets, eventID, numGuests);
    }

    // Create a new booking for a user by a ticket officer method
    public Booking createBookingFor(TicketingOfficer ticketOfficer, int numTickets, String eventID, int numGuests, String userID) {
        return databaseService.createBookingFor(ticketOfficer, numTickets, eventID, numGuests, userID);
    }

    // Cancel a booking for a user and process refund method
    public Refund cancelBooking(User user, String bookingID) {
        // Get the booking associated with the provided ID
        Booking booking = databaseService.getBookingByID(bookingID);

        if (booking != null && booking.getUser().equals(user)) {
            // Process refund and return the Refund object
            return refundService.processRefund(bookingID);
        } else {
            return null; // Permission denied or booking not found
        }
    }

    // Cancel all bookings for an event and process refunds method
    public List<Refund> cancelBooking(EventManager eventManager, Event event) {
        // Check if the event manager has the necessary permissions
        if (eventManagerCanManageEvent(eventManager, event)) {
            // Get all bookings for the event
            List<Booking> bookings = databaseService.getBookingsByEvent(event);

            // Process refunds for each booking and return the list of Refund objects
            return refundService.processRefund(bookings);
        } else {
            return null; // Permission denied
        }
    }

    // Helper method to check if EventManager can manage the event
    private boolean eventManagerCanManageEvent(EventManager eventManager, Event event) {
        // Implementation logic to check if the EventManager has the necessary permissions
        // You may customize this based on your application's requirements
        return event.getManager().equals(eventManager);
    }
}
