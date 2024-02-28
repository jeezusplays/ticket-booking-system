package data;

import java.util.List;
import java.util.Map;

public class Booking {
    private String bookingId;
    private String customerId;
    private int eventID;
    private List<Ticket> tickets;
    private String date;
    private String bookingStatus;

    public Booking(String bookingId, String customerId, int eventID, List<Ticket> tickets, String date, String bookingStatus) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.eventID = eventID;
        this.tickets = tickets;
        this.date = date;
        this.bookingStatus = bookingStatus;
    }

    // Getters
    public String getBookingId() {
        return bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getEventID() {
        return eventID;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public String getDate() {
        return date;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    // Setters
    public void setDate(String date) {
        this.date = date;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    // Other methods
    public Map<String, Object> getBookingDetails() {
        return null;
    }
}
