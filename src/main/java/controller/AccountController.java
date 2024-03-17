package controller;

import service.AccountService;
import user.User;
import user.Customer;
import user.EventManager;
import user.TicketingOfficer;

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
    public void logout(int userID) {
        accountService.logout(userID);
    }

    // Create Customer method
    public Customer createCustomer(int id, String email, String password, String name, String type, double accountBalance) {
        return accountService.createCustomer(id, email, password, name, type, accountBalance);
    }

    // Create Event Manager method
    public EventManager createEventManager(int id, String email, String password, String name, String type) {
        return accountService.createEventManager(id, email, name, password, type);
    }

    // Create Ticket Officer method
    public TicketingOfficer createTicketingOfficer(int id, String email, String password, String name, String type) {
        return accountService.createTicketingOfficer(id, email, password, name, type);
    }
}
