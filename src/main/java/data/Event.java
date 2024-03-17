package data;

import java.time.*;
import java.util.List;
import java.util.Map;
import user.EventManager;
import user.TicketingOfficer;

public class Event {
    private int eventID;
    private int eventManagerID;
    private List<Integer> ticketOfficers;
    private List<Integer> ticketOptions;
    private double ticketCancellationFee;
    private double basePrice;
    private String eventName;
    private String venue;
    private LocalDateTime startTime;
    private int duration;
    private LocalDateTime endTime;
    private List<Ticket> attendance;
    private int numTicketsAvailable;

    public Event(int eventID, int eventManagerID, List<Integer> ticketOfficerIDs, List<Integer> ticketOptionIDs, double ticketCancellationFee, double basePrice, String eventName, String venue, LocalDateTime startTime, int duration, LocalDateTime endTime, List<Ticket> attendance, int numTicketsAvailable) {
        this.eventID = eventID;
        this.eventManagerID = eventManagerID;
        this.ticketOfficers = ticketOfficerIDs;
        this.ticketOptions = ticketOptionIDs;
        this.ticketCancellationFee = ticketCancellationFee;
        this.basePrice = basePrice;
        this.eventName = eventName;
        this.venue = venue;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = endTime;
        this.attendance = attendance;
        this.numTicketsAvailable = numTicketsAvailable;
    }

    // Getters
    public int getEventID() {
        return eventID;
    }

    public int getEventManagerID() {
        return eventManagerID;
    }

    public List<Integer> getTicketOfficers() {
        return ticketOfficers;
    }

    public List<Integer> getTicketOptions() {
        return ticketOptions;
    }

    public double getTicketCancellationFee() {
        return ticketCancellationFee;
    }

    public double getBasePrice() {
        return basePrice;
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

    public int getDuration() {
        return duration;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public List<Ticket> getAttendance() {
        return attendance;
    }
    
    public int getNumTicketsAvailable() {
        return numTicketsAvailable;
    }

    // Setters
    public void setTicketCancellationFee(double ticketCancellationFee) {
        this.ticketCancellationFee = ticketCancellationFee;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    // public void setEndTime(LocalDateTime endTime) {
    //     this.endTime = endTime;
    // }

    public void setNumTicketsAvailable(int numTicketsAvailable) {
        this.numTicketsAvailable = numTicketsAvailable;
    }

    // Other methods
    public Map<String, Object> getEventDetails() {
        // ...
        return null;
    }
}
