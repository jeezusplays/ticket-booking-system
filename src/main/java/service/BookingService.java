package service;

import user.*;

import data.*;

import java.util.HashMap;
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
        return databaseService.getBookings(user.getId());
    }

    // Get all bookings for an event method
    public List<Booking> getBookingsByEvent(Event event) {
        return databaseService.getBookingsByEvent(event.getEventID());
    }

    // Create a new booking for a user method
    public Booking createBooking(User user, int eventID, TicketOption option, HashMap<String, Integer> numTickets) {
        
        return databaseService.createBooking(user.getId(), eventID, option, numTickets);
    }

    // Create a new booking for a user by a ticket officer method
    public Booking createBookingFor(TicketingOfficer ticketOfficer, int eventID, int userID, TicketOption option, HashMap<String, Integer> numTickets) {
        return databaseService.createBookingFor(ticketOfficer.getId(), userID, eventID, option, numTickets);
    }

    // Cancel a booking for a user and process refund method
    public Refund cancelBooking(Customer customer, int bookingID) {
        // Get the booking associated with the provided ID
        Booking booking = databaseService.getBooking(bookingID);

        if (booking != null && booking.getCustomer().equals(customer)) {
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
            List<Booking> bookings = databaseService.getBookingsByEvent(event.getEventID());

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
        return event.geteventManager().equals(eventManager);
    }
}
