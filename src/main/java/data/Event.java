package data;

public class Event {
    private int eventID;
    private String eventName;
    private String venue;
    private String date;
    private double price;
    private int numTicketsAvailable;
    private double ticketCancellationFee;

    public Event(int eventID, String eventName, String venue, String date, double price, int numTicketsAvailable, double ticketCancellationFee) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.venue = venue;
        this.date = date;
        this.price = price;
        this.numTicketsAvailable = numTicketsAvailable;
        this.ticketCancellationFee = ticketCancellationFee;
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

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public int getNumTicketsAvailable() {
        return numTicketsAvailable;
    }

    public double getTicketCancellationFee() {
        return ticketCancellationFee;
    }

    // Setters
    public void setNumTicketsAvailable(int numTicketsAvailable) {
        this.numTicketsAvailable = numTicketsAvailable;
    }

    public void setTicketCancellationFee(double ticketCancellationFee) {
        this.ticketCancellationFee = ticketCancellationFee;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
