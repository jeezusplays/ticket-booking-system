package data;

import java.time;

public class Event {
    private int eventID;
    private String eventName;
    private String venue;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TicketOption ticketOptions;
    private double price;
    private int numTicketsAvailable;

    public Event(int eventID, String eventName, String venue, LocalDateTime startTime, LocalDateTime endTime, TicketOption ticketOptions, double price, int numTicketsAvailable) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ticketOptions = ticketOptions;
        this.price = price;
        this.numTicketsAvailable = numTicketsAvailable;
    }

    // Getters
    public int getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public String getVenue() {
        return venue;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public TicketOption getTicketOptions() {
        return ticketOptions;
    }

    public double getPrice() {
        return price;
    }
    
    public int getNumTicketsAvailable() {
        return numTicketsAvailable;
    }

    // Setters
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    // public void setPrice(double price) {
    //     this.price = price;
    // }

    public void setNumTicketsAvailable(int numTicketsAvailable) {
        this.numTicketsAvailable = numTicketsAvailable;
    }
}
