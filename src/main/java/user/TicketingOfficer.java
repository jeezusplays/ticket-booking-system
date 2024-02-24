package user;

import java.util.List;

public class TicketingOfficer extends User {
    private List<Event> ticketingEvents;

    public TicketingOfficer(int userID, String username, String password, String email, String type, List<Event> ticketingEvents) {
        super(userID, username, password, email, type);
        this.ticketingEvents = ticketingEvents;
    }

    public List<Event> getTicketingEvents() {
        return ticketingEvents;
    }
}
