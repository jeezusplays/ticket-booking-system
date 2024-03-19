package data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import user.*;

public class Booking {
    private String bookingID;
    private Customer customer;
    private TicketingOfficer ticketOfficer;
    private int eventID;
    private TicketOption ticketOption;
    private List<Ticket> tickets;
    private LocalDateTime bookingTime;
    private String bookingStatus;

    public Booking(String bookingID, Customer customer, TicketingOfficer ticketOfficer, int eventID, TicketOption ticketOption, List<Ticket> tickets, LocalDateTime bookingTime, String bookingStatus) {
        this.bookingID = bookingID;
        this.customer = customer;
        this.ticketOfficer = ticketOfficer;
        this.eventID = eventID;
        this.ticketOption = ticketOption;
        this.tickets = tickets;
        this.bookingTime = bookingTime;
        this.bookingStatus = bookingStatus;
    }

    // Getters
    public String getBookingId() {
        return bookingID;
    }

    public User getCustomer() {
        return customer;
    }

    public User getTicketOfficerId() {
        return ticketOfficer;
    }

    public int getEventID() {
        return eventID;
    }

    public TicketOption getTicketOption() {
        return ticketOption;
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
