package data;

import java.util.Map;

public class Ticket {
    private String ticketId;
    private String eventId;
    private String customerId;
    private double price;
    private Boolean isGuest;
    private String date;

    public Ticket(String ticketId, String eventId, String customerId, double price, Boolean isGuest, String date) {
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.customerId = customerId;
        this.price = price;
        this.isGuest = isGuest;
        this.date = date;
    }

    // Getters
    public String getTicketId() {
        return ticketId;
    }

    public String getEventId() {
        return eventId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getPrice() {
        return price;
    }

    public Boolean getIsGuest() {
        return isGuest;
    }

    public String getDate() {
        return date;
    }

    // Setters
    public void setPrice(double price) {
        this.price = price;
    }

    public void setIsGuest(Boolean isGuest) {
        this.isGuest = isGuest;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Other methods
    public Map<String, Object> getTicketDetails() {
        return null;
    }
    
}
