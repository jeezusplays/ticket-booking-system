package service;

import service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import data.*;
import user.*;

public class TestService {
    DatabaseService databaseService;

    public TestService(String host, String name, String username, String password) {
        
        try {
            databaseService = new DatabaseService(host, name, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testUser(){
        System.out.println("Creating a new user...");
        User customer = this.databaseService.createUser("customer@gmail.com", "customer", "customerpassword", "customer");
        System.out.println("User created: " + customer.getEmail() + " " + customer.getName() + " " + customer.getType());
        User eventManager = this.databaseService.createUser("eventmanager@gmail.com", "eventmanager", "empassword", "EventManager");
        System.out.println("User created: " + eventManager.getEmail() + " " + eventManager.getName() + " " + eventManager.getType());
        User ticketingOfficer = this.databaseService.createUser("ticketofficer@gmail.com", "ticketofficer", "topassword", "TicketingOfficer");
        System.out.println("User created: " + ticketingOfficer.getEmail() + " " + ticketingOfficer.getName() + " " + ticketingOfficer.getType());

        // Get user by email and passwsord
        System.out.println("Getting user by email and password...");
        Customer cust = this.databaseService.getCustomer("customer@gmail.com", "customerpassword");
        System.out.println("Customer: " + cust.getEmail());
        EventManager eventM = this.databaseService.getEventManager("eventmanager@gmail.com", "empassword");
        System.out.println("EventManager: " + eventM.getEmail());
        TicketingOfficer ticketOff = this.databaseService.getTicketingOfficer("ticketofficer@gmail.com", "topassword");
        System.out.println("TicketingOfficer: " + ticketOff.getEmail());
    }

    public void testEvent(){
        System.out.println("Creating a new event...");

        HashMap <String,Object> eventDetails = new HashMap<>();
        EventManager eventM = this.databaseService.getEventManager("eventmanager@gmail.com", "empassword");
        LocalDateTime startTime = LocalDateTime.now();
        int duration = 120;
        int numTicketsAvailable = 100;
        List<Ticket> attendance = new ArrayList<Ticket>();

        // details keys,value pairs
        // int eventID,
        // EventManager eventManager,
        // List<Integer> ticketOfficerIDs,
        // List<Integer> ticketOptionIDs,
        // double ticketCancellationFee,
        // double basePrice,
        // String eventName,
        // String venue,
        // LocalDateTime startTime,
        // int duration,
        // LocalDateTime endTime,
        // List<Ticket> attendance,
        // int numTicketsAvailable

        eventDetails.put("eventManager", eventM);
        eventDetails.put("ticketCancellationFee", 0.0);
        eventDetails.put("basePrice", 0.0);
        eventDetails.put("eventName", "Test Event");
        eventDetails.put("venue", "Test Venue");
        eventDetails.put("startTime", startTime);
        eventDetails.put("duration", duration);
        eventDetails.put("endTime", startTime.plusMinutes(duration));
        eventDetails.put("attendance", attendance);
        eventDetails.put("numTicketsAvailable", numTicketsAvailable);

        Event event = this.databaseService.createEvent(eventDetails);

        System.out.println("Event created: " + event.getEventID() + " " + event.getEventName() + " " + event.getVenue() + " " + event.getStartTime() + " " + event.getDuration() + " " + event.getEndTime() + " " + event.getNumTicketsAvailable());

        // Create ticketoption
        System.out.println("Creating a new ticket option...");
        TicketOption ticketOption = this.databaseService.createTicketOption(event.getEventID(), "Test Ticket Option", 1, 1.2);

        // Get event by ID
        System.out.println("Getting event by ID...");
        Event eventByID = this.databaseService.getEvent(event.getEventID());
        System.out.println("Event: " + eventByID.getEventID() + " " + eventByID.getEventName() + " " + eventByID.getVenue() + " " + eventByID.getStartTime() + " " + eventByID.getDuration() + " " + eventByID.getEndTime() + " " + eventByID.getNumTicketsAvailable());

        // Update event
        System.out.println("Updating event...");
        eventByID.setNumTicketsAvailable(50);
        this.databaseService.updateEvent(eventByID);

        // Get all events
        System.out.println("Getting all events...");
        List<Event> allEvents = this.databaseService.getEvents();
        for (Event e : allEvents) {
            System.out.println("Event: " + e.getEventID() + " " + e.getEventName() + " " + e.getVenue() + " " + e.getStartTime() + " " + e.getDuration() + " " + e.getEndTime() + " " + e.getNumTicketsAvailable());
        }

        // Get all events by event manager
        System.out.println("Getting all events by event manager...");
        List<Event> eventsByEventManager = this.databaseService.getManagedEvents(eventM.getId());
        for (Event e : eventsByEventManager) {
            System.out.println("Event: " + e.getEventID() + " " + e.getEventName() + " " + e.getVenue() + " " + e.getStartTime() + " " + e.getDuration() + " " + e.getEndTime() + " " + e.getNumTicketsAvailable());
        }


    }


}
