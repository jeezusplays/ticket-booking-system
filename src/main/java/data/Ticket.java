package data;

import java.util.Map;

public class Ticket {
    private String ticketID;
    // private String eventID;
    // private String customerID;
    // private double price;
    private Boolean isGuest;
    private Boolean attended;

    // public Ticket(String ticketID, String eventID, String customerID, double price, Boolean isGuest) {
    public Ticket(String ticketID, Boolean isGuest) {
        this.ticketID = ticketID;
        // this.eventID = eventID;
        // this.customerID = customerID;
        // this.price = price;
        this.isGuest = isGuest;
        this.attended = false;
    }

    // Getters
    public String getticketID() {
        return ticketID;
    }

    // public String geteventID() {
    //     return eventID;
    // }

    // public String getcustomerID() {
    //     return customerID;
    // }

    // public double getPrice() {
    //     return price;
    // }

    public Boolean getIsGuest() {
        return isGuest;
    }

    public Boolean getAttended() {
        return attended;
    }

    // Setters
    // public void setPrice(double price) {
    //     this.price = price;
    // }

    // public void setIsGuest(Boolean isGuest) {
    //     this.isGuest = isGuest;
    // }

    // Other methods
    public Map<String, Object> getTicketDetails() {
        return null;
    }
    
}
