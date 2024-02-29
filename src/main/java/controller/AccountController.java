public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Login method
    public User login(String email, String password) {
        return accountService.login(email, password);
    }

    // Logout method
    public void logout() {
        accountService.logout();
    }

    // Create Customer method
    public Customer createCustomer(String email, String name, String password) {
        return accountService.createCustomer(email, name, password);
    }

    // Create Event Manager method
    public EventManager createEventManager(String email, String name, String password) {
        return accountService.createEventManager(email, name, password);
    }

    // Create Ticket Officer method
    public TicketOfficer createTicketOfficer(String email, String name, String password) {
        return accountService.createTicketOfficer(email, name, password);
    }
}
