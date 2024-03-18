package service;

public class AccountService {
    private DatabaseService databaseService;

    public AccountService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    // Login method
    public User login(String email, String password) {
        return databaseService.authenticateUser(email, password);
    }

    // Logout method
    public void logout() {
        // Perform logout operations if needed
    }

    // Create Customer method
    public Customer createCustomer(String email, String name, String password) {
        Customer customer = new Customer(email, name, password); // Create a new customer
        databaseService.saveUser(customer); // Save the customer to the database
        return customer;
    }

    // Create Event Manager method
    public EventManager createEventManager(String email, String name, String password) {
        EventManager eventManager = new EventManager(email, name, password); // Create a new event manager
        databaseService.saveUser(eventManager); // Save the event manager to the database
        return eventManager;
    }

    // Create Ticket Officer method
    public TicketOfficer createTicketOfficer(String email, String name, String password) {
        TicketOfficer ticketOfficer = new TicketOfficer(email, name, password); // Create a new ticket officer
        databaseService.saveUser(ticketOfficer); // Save the ticket officer to the database
        return ticketOfficer;
    }
}
