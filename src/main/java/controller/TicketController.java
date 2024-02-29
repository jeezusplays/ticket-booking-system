import java.util.List;

public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // Get all tickets for a user method
    public List<Ticket> getTickets(User user) {
        return ticketService.getTickets(user);
    }

    // Verify a ticket method
    public boolean verifyTicket(String ticketID) {
        return ticketService.verifyTicket(ticketID);
    }
}
