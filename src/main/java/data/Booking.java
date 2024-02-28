package data;

import java.time;
import java.util.List;
import java.util.Map;

public class Booking {
    private String bookingID;
    private String customerID;
    private int eventID;
    private List<Ticket> tickets;
    private LocalDateTime bookingTime;
    private String bookingStatus;

    public Booking(String bookingId, String customerId, int eventId, List<Ticket> tickets, LocalDateTime bookingTime, String bookingStatus) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.eventId = eventId;
        this.tickets = tickets;
        this.bookingTime = bookingTime;
        this.bookingStatus = bookingStatus;
    }

    // Getters
    public String getBookingId() {
        return bookingID;
    }

    public String getCustomerId() {
        return customerID;
    }

    public int getEventID() {
        return eventID;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    // Setters
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
    
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    // Other methods
    public Map<String, Object> getBookingDetails() {
        return null;
    }
}
