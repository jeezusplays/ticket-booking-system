package data;

import java.util.Map;

public class Ticket {
    private Integer ticketID;
    private Integer eventID;
    // private String customerID;
    // private double price;
    private Boolean isGuest;
    private Boolean attended;

    public Ticket(Integer ticketID, Boolean isGuest, Boolean attended) {
        this.ticketID = ticketID;
        this.isGuest = isGuest;
        this.attended = attended;
    }

    // Getters
    public Integer getticketID() {
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
