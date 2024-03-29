package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.Booking;
import data.Event;
import data.Refund;
import data.Ticket;
import data.TicketOption;
import user.Customer;
import user.EventManager;
import user.TicketingOfficer;
import user.User;


public class DatabaseService {
    private String host;
    private String name;
    private String username;
    private String password;
    private Connection connection;

    // Constructor
    public DatabaseService(String host, String name, String username, String password) throws SQLException {
        this.host = host;
        this.name = name;
        this.username = username;
        this.password = password;
        this.connect();
    }


    // Method to establish a database connection
    public void connect() throws SQLException {
        String url = "jdbc:mysql://" + this.host + "/" + this.name;
        this.connection = DriverManager.getConnection(url, this.username, this.password);
    }

    // Method to close the database connection
    public void disconnect() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
        }
    }

    // Methods for User Management

    public User createUser(String email, String name, String password, String type) {
        // Implement user creation logic and store user information in the database
        // Return the created User object

        String query = "INSERT INTO users (email, name, password, type) VALUES (?, ?, ?, ?)";

        // Try-with-resources to ensure that resources are freed properly
        try (PreparedStatement pstmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, email);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
            pstmt.setString(4, type);

            int success = pstmt.executeUpdate();

            if (success > 0) {
                // Retrieve the generated keys
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userID = generatedKeys.getInt(1);
                        // Create the appropriate user subclass object based on the user type
                        // EventManager
                        if (type.equals("EventManager")) {
                            User newUser = new EventManager(
                                userID,
                                email,
                                password,
                                name, 
                                type);
                            return newUser;

                        }

                        // TicketingOfficer
                        else if (type.equals("TicketingOfficer")) {
                            User newUser = new TicketingOfficer(
                                userID,
                                email,
                                password,
                                name, 
                                type);
                            return newUser;
                        }
                        // Customer
                        else {
                            // Add balance to customer
                            // TODO - Change to correct db schema
                            int success2 = this.connection.createStatement().executeUpdate("INSERT INTO customers (user_id, balance) VALUES (" + userID + ", 0.0)");
                            
                            if (success2 == 1) {
                                User newUser = new Customer(
                                    userID,
                                    email,
                                    password,
                                    name, 
                                    type,
                                    0.0);
                                return newUser;
                            }
                            else {
                                throw new RuntimeException("Error creating user: balance not added to customer");
                            }
                        }
                    } else {
                        throw new SQLException("Creating user failed, no user ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating user failed, no rows affected.");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        
    }

    public User getUser(String email, String password){
        // Retrieve a user from the database based on their email and password
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        
        // Try-with-resources to ensure that resources are freed properly
        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet user = pstmt.executeQuery();

            if (user.next()) {
                // TODO - Associate the user with the appropriate subclass based on the user type
                ResultSet subclass = this.connection.createStatement().executeQuery("SELECT * FROM " + user.getString("type").toLowerCase() + "s WHERE user_id = " + user.getInt("id"));

                if (subclass.next()) {
                    // Create the appropriate user subclass object based on the user type
                    // EventManager
                    if (user.getString("type").equals("EventManager")) {
                        User newUser = new EventManager(
                            user.getInt("id"),
                            user.getString("email"),
                            user.getString("password"),
                            user.getString("name"), 
                            user.getString("type"));
                        return newUser;

                    }

                    // TicketingOfficer
                    else if (user.getString("type").equals("TicketingOfficer")) {
                        User newUser = new TicketingOfficer(
                            user.getInt("id"),
                            user.getString("email"),
                            user.getString("password"),
                            user.getString("name"), 
                            user.getString("type"));
                        return newUser;
                    }
                    // Customer
                    else {
                        // Add balance to customer
                        // TODO - Change to correct db schema
                        ResultSet customer = this.connection.createStatement().executeQuery("SELECT * FROM customers WHERE user_id = " + user.getInt("id"));
                        
                        if (customer.next()) {
                            User newUser = new Customer(
                                user.getInt("id"),
                                user.getString("email"),
                                user.getString("password"),
                                user.getString("name"), 
                                user.getString("type"),
                                customer.getDouble("balance"));
                            return newUser;
                        }
                        else {
                            throw new RuntimeException("Error getting user: balance not retrieved for customer");
                        }
                    }
                }
                else {
                    throw new RuntimeException("Error getting user: subclass not found");
                }
            }
            else {
                throw new RuntimeException("Error getting user: user not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public Customer getCustomer(String email, String password){
        User user = getUser(email, password);
        if (user instanceof Customer) {
            return (Customer) user;
        }
        else {
            throw new RuntimeException("Error getting customer: user is not a customer");
        }
    }

    public EventManager getEventManager(String email, String password){
        User user = getUser(email, password);
        if (user instanceof EventManager) {
            return (EventManager) user;
        }
        else {
            throw new RuntimeException("Error getting event manager: user is not an event manager");
        }
    }

    public TicketingOfficer getTicketingOfficer(String email, String password){
        if (getUser(email, password) == null){
            return null;
        }
        User user = getUser(email, password);
        if (user instanceof TicketingOfficer) {
            return (TicketingOfficer) user;
        }
        else {
            throw new RuntimeException("Error getting ticketing officer: user is not a ticketing officer");
        }
    }

    private User getUser(int userID){

        if (userID == 0){
            return null;
        }

        String query = "SELECT * FROM users WHERE id = ?";

        // Try-with-resources to ensure that resources are freed properly
        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setInt(1, userID);

            ResultSet user = pstmt.executeQuery();

            if (user.next()) {
                // TODO - Associate the user with the appropriate subclass based on the user type
                ResultSet subclass = this.connection.createStatement().executeQuery("SELECT * FROM " + user.getString("type").toLowerCase() + "s WHERE user_id = " + user.getInt("id"));

                if (subclass.next()) {
                    // Create the appropriate user subclass object based on the user type
                    // EventManager
                    if (user.getString("type").equals("EventManager")) {
                        User newUser = new EventManager(
                            user.getInt("id"),
                            user.getString("email"),
                            user.getString("password"),
                            user.getString("name"), 
                            user.getString("type"));
                        return newUser;

                    }

                    // TicketingOfficer
                    else if (user.getString("type").equals("TicketingOfficer")) {
                        User newUser = new TicketingOfficer(
                            user.getInt("id"),
                            user.getString("email"),
                            user.getString("password"),
                            user.getString("name"), 
                            user.getString("type"));
                        return newUser;
                    }
                    // Customer
                    else {
                        // Add balance to customer
                        // TODO - Change to correct db schema
                        ResultSet customer = this.connection.createStatement().executeQuery("SELECT * FROM customers WHERE user_id = " + user.getInt("id"));
                        
                        if (customer.next()) {
                            User newUser = new Customer(
                                user.getInt("id"),
                                user.getString("email"),
                                user.getString("password"),
                                user.getString("name"), 
                                user.getString("type"),
                                customer.getDouble("balance"));
                            return newUser;
                        }
                        else {
                            throw new RuntimeException("Error getting user: balance not retrieved for customer");
                        }
                    }
                }
                else {
                    throw new RuntimeException("Error getting user: subclass not found");
                }
            }
            else {
                throw new RuntimeException("Error getting user: user not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private Customer getCustomer(int userID){
        User user = getUser(userID);
        if (user instanceof Customer) {
            return (Customer) user;
        }
        else {
            throw new RuntimeException("Error getting customer: user is not a customer");
        }
    }

    private EventManager getEventManager(int userID){
        User user = getUser(userID);
        if (user instanceof EventManager) {
            return (EventManager) user;
        }
        else {
            throw new RuntimeException("Error getting event manager: user is not an event manager");
        }
    }

    private TicketingOfficer getTicketingOfficer(int userID){
        if (getUser(userID) == null){
            return null;
        }
        User user = getUser(userID);
        if (user instanceof TicketingOfficer) {
            return (TicketingOfficer) user;
        }
        else {
            throw new RuntimeException("Error getting ticketing officer: user is not a ticketing officer");
        }
    }

    private Map<Integer, Boolean> addOfficerToEvent(int eventManagerID, int eventID, List<Integer> userIDs) {
        // Implement logic to associate officers with an event managed by the specified event manager
        // Return a map indicating the success or failure of adding each officer

        HashMap<Integer, Boolean> results = new HashMap<Integer,Boolean>();

        // Check if the event manager is associated with the event
        if (getManagedEvents(eventManagerID).contains(getEvent(eventID))) {
            // Check if the officers are ticketing officers
            for (int userID : userIDs) {
                if (getTicketingOfficer(userID) == null) {
                    throw new RuntimeException("Error adding officer to event: user is not a ticketing officer");
                }
            }
            for (int userID : userIDs) {
                String query = "INSERT INTO event_ticket_officers (eventID, ticketOfficerID) VALUES (?, ?)";
                
                // Try-with-resources to ensure that resources are freed properly
                try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
                    pstmt.setInt(1, eventID);
                    pstmt.setInt(2, userID);

                    int success = pstmt.executeUpdate();
                    results.put(userID, success > 0);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage());
                }
            }
            return results;
        }
        else {
            throw new RuntimeException("Error adding officer to event: event manager not associated with event");
        }

    }

    public Map<Integer, Boolean> addOfficerToEvent(String email, String password ,int eventID, List<Integer> userIDs) {
        // Implement logic to associate officers with an event managed by the specified event manager
        // Return a map indicating the success or failure of adding each officer

        HashMap<Integer, Boolean> results = new HashMap<Integer,Boolean>();

        EventManager eventManager = getEventManager(email, password);

        // Check if the event manager is associated with the event
        if (getManagedEvents(eventManager.getId()).contains(getEvent(eventID))) {
            // Check if the officers are ticketing officers
            for (int userID : userIDs) {
                if (getTicketingOfficer(userID) == null) {
                    throw new RuntimeException("Error adding officer to event: user is not a ticketing officer");
                }
            }
            for (int userID : userIDs) {
                String query = "INSERT INTO event_ticket_officers (eventID, ticketOfficerID) VALUES (?, ?)";
                
                // Try-with-resources to ensure that resources are freed properly
                try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
                    pstmt.setInt(1, eventID);
                    pstmt.setInt(2, userID);

                    int success = pstmt.executeUpdate();
                    results.put(userID, success > 0);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage());
                }
            }
            return results;
        }
        else {
            throw new RuntimeException("Error adding officer to event: event manager not associated with event");
        }
    }

    // Methods for Event Management

    public List<Ticket> getTicketsByEventID(int eventID) {
        // Retrieve a list of tickets associated with the specified event from the database
        List<Ticket> tickets = new ArrayList<Ticket>();
        String query1 = "SELECT * FROM bookings WHERE event_id = ?";
        String query2 = "SELECT * FROM tickets WHERE booking_id = ?";

        // Try-with-resources to ensure that resources are freed properly
        try (PreparedStatement pstmt1 = this.connection.prepareStatement(query1)) {
            pstmt1.setInt(1, eventID);
            ResultSet bookings_results = pstmt1.executeQuery();

            while (bookings_results.next()) {
                try (PreparedStatement pstmt2 = this.connection.prepareStatement(query2)) {
                    pstmt2.setInt(1, bookings_results.getInt("bookingID"));
                    ResultSet tickets_results = pstmt2.executeQuery();

                    while (tickets_results.next()) {
                        Ticket ticket = new Ticket(
                            tickets_results.getInt("ticketID"),
                            tickets_results.getBoolean("isGuest"),
                            tickets_results.getBoolean("attended")
                        );
                        tickets.add(ticket);
                    }
                }
            }
            return tickets;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Integer> getTicketOfficersByEventID(int eventID){
        String query = "SELECT * FROM event_ticket_officers WHERE event_id = ?";
        List<Integer> ticketOfficers = new ArrayList<Integer>();

        // Try-with-resources to ensure that resources are freed properly
        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setInt(1, eventID);
            ResultSet ticketOfficers_results = pstmt.executeQuery();

            while (ticketOfficers_results.next()) {
                ticketOfficers.add(ticketOfficers_results.getInt("ticketOfficerID"));
            }
            return ticketOfficers;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Event> getEvents() {
        String query = "SELECT * FROM events";
        List<Event> events = new ArrayList<Event>();

        // Try-with-resources to ensure that resources are freed properly
        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            ResultSet events_results = pstmt.executeQuery();

            while (events_results.next()) {
                Event event = new Event(
                    events_results.getInt("eventID"),
                    getEventManager(events_results.getInt("eventManagerID")),
                    getTicketOfficersByEventID(events_results.getInt("eventID")),
                    getTicketOptionsByEventID(events_results.getInt("eventID")),
                    events_results.getDouble("ticketCancellationFee"),
                    events_results.getDouble("basePrice"),
                    events_results.getString("eventName"),
                    events_results.getString("venue"),
                    events_results.getTimestamp("startTime").toLocalDateTime(),
                    events_results.getInt("duration"),
                    events_results.getTimestamp("endTime").toLocalDateTime(),
                    getTicketsByEventID(events_results.getInt("eventID")),
                    events_results.getInt("numTicketsAvailable")
                );
                events.add(event);
            }
            return events;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public Event getEvent(int eventID) {
        String query = "SELECT * FROM events WHERE eventID = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setInt(1, eventID);
            ResultSet events_result = pstmt.executeQuery();

            // Parse the results and create Event objects
            while (events_result.next()) {

                Event event = new Event(
                    events_result.getInt("eventID"),
                    getEventManager(events_result.getInt("eventManagerID")),
                    getTicketOfficersByEventID(events_result.getInt("eventID")),
                    getTicketOptionsByEventID(events_result.getInt("eventID")),
                    events_result.getDouble("ticketCancellationFee"),
                    events_result.getDouble("basePrice"),
                    events_result.getString("eventName"),
                    events_result.getString("venue"),
                    events_result.getTimestamp("startTime").toLocalDateTime(),
                    events_result.getInt("duration"),
                    events_result.getTimestamp("endTime").toLocalDateTime(),
                    getTicketsByEventID(events_result.getInt("eventID")),
                    events_result.getInt("numTicketsAvailable")
                );
                return event;
            }
            throw new RuntimeException("Error getting event: event not found");
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public List<Event> getManagedEvents(int eventManagerID) {

        String query = "SELECT * FROM events WHERE eventManagerID = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setInt(1, eventManagerID);
            ResultSet events_results = pstmt.executeQuery();
            List<Event> events = new ArrayList<Event>();

            // Parse the results and create Event objects
            while (events_results.next()) {
                Event event = new Event(
                    events_results.getInt("eventID"),
                    getEventManager(events_results.getInt("eventManagerID")),
                    getTicketOfficersByEventID(events_results.getInt("eventID")),
                    getTicketOptionsByEventID(events_results.getInt("eventID")),
                    events_results.getDouble("ticketCancellationFee"),
                    events_results.getDouble("basePrice"),
                    events_results.getString("eventName"),
                    events_results.getString("venue"),
                    events_results.getTimestamp("startTime").toLocalDateTime(),
                    events_results.getInt("duration"),
                    events_results.getTimestamp("endTime").toLocalDateTime(),
                    getTicketsByEventID(events_results.getInt("eventID")),
                    events_results.getInt("numTicketsAvailable")
                );
                events.add(event);
            }
            return events;

        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        
    }

    public Event updateEvent(Event event) {

        String query = "UPDATE events SET eventManagerID = ?, ticketCancellationFee = ?, basePrice = ?, eventName = ?, venue = ?, startTime = ?, duration = ?, endTime = ?, numTicketsAvailable = ? WHERE eventID = ?";

        // Update the details of the specified event in the database
        int eventID = event.getEventID();

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setInt(1, event.geteventManager().getId());
            pstmt.setFloat(2, (float) event.getTicketCancellationFee());
            pstmt.setFloat(3, (float) event.getBasePrice());
            pstmt.setString(4, event.getEventName());
            pstmt.setString(5, event.getVenue());
            pstmt.setTimestamp(6, java.sql.Timestamp.valueOf(event.getStartTime()));
            pstmt.setInt(7, event.getDuration());
            pstmt.setTimestamp(8, java.sql.Timestamp.valueOf(event.getEndTime()));
            pstmt.setInt(9, event.getNumTicketsAvailable());
            pstmt.setInt(10, eventID);

            int success = pstmt.executeUpdate();

            if (success > 0) {
                return getEvent(eventID);
            }
            else {
                throw new RuntimeException("Error updating event: event not updated");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public Event createEvent(Map<String, Object> details) {

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
        


        String query = "INSERT INTO events (eventManagerID, ticketCancellationFee, basePrice, eventName, venue, startTime, duration, endTime, numTicketsAvailable) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        // check ticketOptions is formatted correctly
        if (!(details.get("ticketOptions") instanceof List<?>)) {
            throw new RuntimeException("Error creating event: ticketOptions not a list");
        }

        // Check if id belongs to an event manager
        if (getEventManager((int) details.get("eventManagerID")) == null) {
            throw new RuntimeException("Error creating event: event manager not found");
        }
        

        // Try-with-resources to ensure that resources are freed properly
        try (PreparedStatement pstmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, (int) details.get("eventManagerID"));
            pstmt.setFloat(2, (float) details.get("ticketCancellationFee"));
            pstmt.setFloat(3, (float) details.get("basePrice"));
            pstmt.setString(4, (String) details.get("eventName"));
            pstmt.setString(5, (String) details.get("venue"));
            pstmt.setTimestamp(6, java.sql.Timestamp.valueOf((String) details.get("startTime")));
            pstmt.setInt(7, (int) details.get("duration"));
            pstmt.setTimestamp(8, java.sql.Timestamp.valueOf((String) details.get("endTime")));
            pstmt.setInt(9, (int) details.get("numTicketsAvailable"));

            int success = pstmt.executeUpdate();

            if (success > 0) {
                // Retrieve the generated keys
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int eventID = generatedKeys.getInt(1);

                        // Add ticketOptions
                        List<TicketOption> ticketOptions = (List<TicketOption>) details.get("ticketOptions");

                        for (TicketOption ticketOption : ticketOptions) {
                            String query2 = "INSERT INTO event_ticket_options (eventID) VALUES (?)";
                            try (PreparedStatement pstmt2 = this.connection.prepareStatement(query2)) {
                                pstmt2.setInt(1, eventID);
                                pstmt2.executeUpdate();
                            }
                        }
                        return getEvent(eventID);
                    } else {
                        throw new SQLException("Creating event failed, no event ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating event failed, no rows affected.");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    // Methods for Ticket and Booking Operations

    public HashMap<Integer,Boolean> createTicket(int bookingID, HashMap<String,Integer> ticketMap) {

        int guest = ticketMap.get("guest");
        int total = ticketMap.get("total");

        // TicketID, success
        HashMap<Integer,Boolean> results = new HashMap<Integer,Boolean>();


        for (int i = 0; i < total; i++) {
            String query = "INSERT INTO tickets (bookingID, isGuest) VALUES (?, ?)";
            
            // Try-with-resources to ensure that resources are freed properly
            try (PreparedStatement pstmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, bookingID);
                pstmt.setBoolean(2, i < guest);

                int success = pstmt.executeUpdate();

                if (success > 0) {
                    // Retrieve the generated keys
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            results.put(generatedKeys.getInt(1), true);
                        } else {
                            results.put(i, false);
                        }
                    }
                } else {
                    results.put(i, false);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        // Create tickets for the specified event and quantity in the database
        return results;
    }

    public Booking getBooking(int bookingID) {
        // Retrieve a booking from the database based on the booking ID
        String query = "SELECT * FROM bookings WHERE bookingID = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setInt(1, bookingID);
            ResultSet booking = pstmt.executeQuery();

            if (booking.next()) {
                return new Booking(
                    booking.getInt("bookingID"),
                    getCustomer(booking.getInt("customerID")),
                    getTicketingOfficer(booking.getInt("ticketOfficerID")),
                    booking.getInt("eventID"),
                    getTicketOptionByBookingID(booking.getInt("bookingID")),
                    getTicketsByBookingID(booking.getInt("bookingID")),
                    booking.getTimestamp("bookingTime").toLocalDateTime(),
                    booking.getString("bookingStatus")
                );
            }
            else {
                throw new RuntimeException("Error getting booking: booking not found");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public Ticket getTicket(int ticketID) {
        // Retrieve a ticket from the database based on the ticket ID
        String query = "SELECT * FROM tickets WHERE ticketID = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setInt(1, ticketID);
            ResultSet ticket = pstmt.executeQuery();

            if (ticket.next()) {
                return new Ticket(
                    ticket.getInt("ticketID"),
                    ticket.getBoolean("isGuest"),
                    ticket.getBoolean("attended")
                );
            }
            else {
                throw new RuntimeException("Error getting ticket: ticket not found");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean checkTicketAvailability(int eventID, int quantity) {
        
        String query = "SELECT * FROM events WHERE eventID = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setInt(1, eventID);
            ResultSet event = pstmt.executeQuery();

            if (event.next()) {
                return event.getInt("numTicketsAvailable") >= quantity;
            }
            else {
                throw new RuntimeException("Error checking ticket availability: event not found");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public boolean verifyTicket(int ticketID) {
        
        // Check if the specified ticket exist and has not been verified
        String query = "SELECT * FROM tickets WHERE ticketID = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setInt(1, ticketID);
            ResultSet ticket = pstmt.executeQuery();

            if (ticket.next()) {
                // Update the ticket to mark it as verified
                String query2 = "UPDATE tickets SET attended = ? WHERE ticketID = ?";
                try (PreparedStatement pstmt2 = this.connection.prepareStatement(query2)) {
                    pstmt2.setBoolean(1, true);
                    pstmt2.setInt(2, ticketID);

                    int success = pstmt2.executeUpdate();

                    return success > 0;
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage());
                }
            }
            else {
                throw new RuntimeException("Error verifying ticket: ticket not found");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public List<Ticket> getTicketsByBookingID(int bookingID) {
        // Retrieve a list of tickets associated with the specified booking from the database
        String query = "SELECT * FROM tickets WHERE bookingID = ?";
        List<Ticket> tickets = new ArrayList<Ticket>();

        // Try-with-resources to ensure that resources are freed properly
        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setInt(1, bookingID);
            ResultSet tickets_results = pstmt.executeQuery();

            while (tickets_results.next()) {
                Ticket ticket = new Ticket(
                    tickets_results.getInt("ticketID"),
                    tickets_results.getBoolean("isGuest"),
                    tickets_results.getBoolean("attended")
                );
                tickets.add(ticket);
            }
            return tickets;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public TicketOption getTicketOptionByBookingID(int bookingID) {
        String query = "SELECT * FROM ticket_options WHERE bookingID = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setInt(1, bookingID);
            ResultSet ticketOption = pstmt.executeQuery();

            if (ticketOption.next()) {
                return new TicketOption(
                    ticketOption.getInt("ticketOptionID"),
                    ticketOption.getInt("totalAvailable"),
                    ticketOption.getInt("priceMultipler"),
                    ticketOption.getString("name")
                );
            }
            else {
                throw new RuntimeException("Error getting ticket option: ticket option not found");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Integer> getTicketOptionsByEventID(Integer eventID){
        String query = "SELECT * FROM event_ticket_options WHERE event_id = ?";
        List<Integer> ticketOptions = new ArrayList<Integer>();

        // Try-with-resources to ensure that resources are freed properly
        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setInt(1, eventID);
            ResultSet ticketOptions_results = pstmt.executeQuery();

            while (ticketOptions_results.next()) {
                ticketOptions.add(ticketOptions_results.getInt("ticketOptionID"));
            }
            return ticketOptions;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Ticket> getTickets(int userID) {
        
        try{
            // Get bookings for the user
            List<Booking> bookings = getBookings(userID);

            // Get tickets for each booking
            List<Ticket> tickets = new ArrayList<Ticket>();
            for (Booking booking : bookings) {
                tickets.addAll(booking.getTickets());
        }

        return tickets;

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Booking> getBookings(int userID) {
        
        String query = "SELECT * FROM bookings WHERE customerID = ?";
        List<Booking> bookings = new ArrayList<Booking>();

        // Try-with-resources to ensure that resources are freed properly
        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setInt(1, userID);
            ResultSet bookings_results = pstmt.executeQuery();

            while (bookings_results.next()) {
                Booking booking = new Booking(
                    bookings_results.getInt("bookingID"),
                    getCustomer(bookings_results.getInt("customerID")),
                    getTicketingOfficer(bookings_results.getInt("ticketOfficerID")),
                    bookings_results.getInt("eventID"),
                    getTicketOptionByBookingID(bookings_results.getInt("bookingID")),
                    getTicketsByBookingID(bookings_results.getInt("bookingID")),
                    bookings_results.getTimestamp("bookingTime").toLocalDateTime(),
                    bookings_results.getString("bookingStatus")
                );
                bookings.add(booking);
            }
            return bookings;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public List<Booking> getBookingsByEvent(int eventID) {
        
        String query = "SELECT * FROM bookings WHERE eventID = ?";
        List<Booking> bookings = new ArrayList<Booking>();

        // Try-with-resources to ensure that resources are freed properly
        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setInt(1, eventID);
            ResultSet bookings_results = pstmt.executeQuery();

            while (bookings_results.next()) {
                Booking booking = new Booking(
                    bookings_results.getInt("bookingID"),
                    getCustomer(bookings_results.getInt("customerID")),
                    getTicketingOfficer(bookings_results.getInt("ticketOfficerID")),
                    bookings_results.getInt("eventID"),
                    getTicketOptionByBookingID(bookings_results.getInt("bookingID")),
                    getTicketsByBookingID(bookings_results.getInt("bookingID")),
                    bookings_results.getTimestamp("bookingTime").toLocalDateTime(),
                    bookings_results.getString("bookingStatus")
                );
                bookings.add(booking);
            }
            return bookings;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }
    
    public TicketOption createTicketOption(int eventID, String name, int totalAvailable, double priceMultiplier) {
        String query = "INSERT INTO ticket_options (eventID, name, totalAvailable, priceMultiplier) VALUES (?, ?, ?, ?)";

        // Try-with-resources to ensure that resources are freed properly
        try (PreparedStatement pstmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, eventID);
            pstmt.setString(2, name);
            pstmt.setInt(3, totalAvailable);
            pstmt.setDouble(4, priceMultiplier);

            int success = pstmt.executeUpdate();

            if (success > 0) {
                // Retrieve the generated keys
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return new TicketOption(
                            generatedKeys.getInt(1),
                            totalAvailable,
                            priceMultiplier,
                            name
                        );
                    } else {
                        throw new SQLException("Creating ticket option failed, no ticket option ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating ticket option failed, no rows affected.");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public Booking createBooking(int userID, int eventID, TicketOption ticketOption, HashMap<String, Integer> ticketMap) {
        
        // CREATE TABLE IF NOT EXISTS Booking (
        //     bookingID INT PRIMARY KEY AUTO_INCREMENT,
        //     eventID INT NOT NULL,
        //     ticketOptionID INT NOT NULL,
        //     customerID INT NOT NULL,
        //     ticketOfficerID INT,
        //     status enum('pending', 'confirmed', 'cancelled') NOT NULL,
        //     bookedTime DATETIME NOT NULL,
        //     FOREIGN KEY (eventID) REFERENCES Event(eventID),
        //     FOREIGN KEY (ticketOptionID) REFERENCES TicketOption(ticketOptionID),
        //     FOREIGN KEY (customerID) REFERENCES Customer(userID),
        //     FOREIGN KEY (ticketOfficerID) REFERENCES TicketOfficer(userID)
        // );
        
        String query = "INSERT INTO bookings (eventID, ticketOptionID, customerID, status, bookedTime) VALUES (?, ?, ?, ?, ?)";
        
        int quantity = ticketMap.get("total");

        // Try-with-resources to ensure that resources are freed properly
        // Check if the ticket option is available
        if (checkTicketAvailability(eventID, quantity)) {
            // Create the booking
            try (PreparedStatement pstmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, eventID);
                pstmt.setInt(2, ticketOption.getTicketOptionID());
                pstmt.setInt(3, userID);
                pstmt.setString(4, "pending");
                pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));

                int success = pstmt.executeUpdate();

                if (success > 0) {
                    // Retrieve the generated keys
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int bookingID = generatedKeys.getInt(1);
                            // Create the tickets
                            HashMap<Integer,Boolean> ticketResults = createTicket(bookingID, ticketMap);
                            List<Ticket> tickets = new ArrayList<Ticket>();
                            for (int ticketID : ticketResults.keySet()) {
                                tickets.add(new Ticket(
                                    ticketID,
                                    getTicket(ticketID).getIsGuest(),
                                    false
                                ));
                            }
                            return new Booking(
                                bookingID,
                                getCustomer(userID),
                                null,
                                eventID,
                                ticketOption,
                                tickets,
                                java.time.LocalDateTime.now(),
                                "pending"
                            );
                        } else {
                            throw new SQLException("Creating booking failed, no booking ID obtained.");
                        }
                    }
                } else {
                    throw new SQLException("Creating booking failed, no rows affected.");
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        else {
            throw new RuntimeException("Error creating booking: ticket option not available");
        }
    }

    public Booking createBookingFor(int ticketOfficerID, int userID, int eventID, TicketOption ticketOption, HashMap<String, Integer> ticketMap) {
        String query = "INSERT INTO bookings (eventID, ticketOptionID, customerID, ticketOfficerID, status, bookedTime) VALUES (?, ?, ?, ?, ?, ?)";
        
        // Try-with-resources to ensure that resources are freed properly
        // Check if the ticket option is available

        int quantity = ticketMap.get("total");

        if (checkTicketAvailability(eventID, quantity)) {
            // Create the booking
            try (PreparedStatement pstmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, eventID);
                pstmt.setInt(2, ticketOption.getTicketOptionID());
                pstmt.setInt(3, userID);
                pstmt.setInt(4, ticketOfficerID);
                pstmt.setString(5, "pending");
                pstmt.setTimestamp(6, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));

                int success = pstmt.executeUpdate();

                if (success > 0) {
                    // Retrieve the generated keys
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int bookingID = generatedKeys.getInt(1);
                            // Create the tickets
                            HashMap<Integer,Boolean> ticketResults = createTicket(bookingID, ticketMap);
                            List<Ticket> tickets = new ArrayList<Ticket>();
                            for (int ticketID : ticketResults.keySet()) {
                                tickets.add(new Ticket(
                                    ticketID,
                                    getTicket(ticketID).getIsGuest(),
                                    false
                                ));
                            }
                            return new Booking(
                                bookingID,
                                getCustomer(userID),
                                getTicketingOfficer(ticketOfficerID),
                                eventID,
                                ticketOption,
                                tickets,
                                java.time.LocalDateTime.now(),
                                "pending"
                            );
                        } else {
                            throw new SQLException("Creating booking failed, no booking ID obtained.");
                        }
                    }
                } else {
                    throw new SQLException("Creating booking failed, no rows affected.");
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        else {
            throw new RuntimeException("Error creating booking: ticket option not available");
        }

    }

    public boolean cancelBooking(int bookingID, int userID) {
        String query = "UPDATE bookings SET status = ? WHERE bookingID = ? AND customerID = ?";

        // Try-with-resources to ensure that resources are freed properly
        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setString(1, "cancelled");
            pstmt.setInt(2, bookingID);
            pstmt.setInt(3, userID);

            int success = pstmt.executeUpdate();

            return success > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }


    }

    // Method for Refund Creation

    public Refund createRefund(int bookingID, int userID) {
        String query = "INSERT INTO refunds (bookingID, customerID, refundTime) VALUES (?, ?, ?)";
        Event event = getEvent(getBooking(bookingID).getEventID());
        double cancelationFee = event.getTicketCancellationFee();
        double multiplier = (double) getBooking(bookingID).getTicketOption().getPriceMultiplier();
        int numberOfTickets = getBooking(bookingID).getTickets().size();

        // Calculate the refund amount
        double refundAmount = (multiplier * event.getBasePrice() * numberOfTickets) - cancelationFee;

        // Set refund date as current date
        LocalDateTime refundDate = java.time.LocalDateTime.now();

        // Try-with-resources to ensure that resources are freed properly
        try (PreparedStatement pstmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, bookingID);
            pstmt.setInt(2, userID);
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));

            int success = pstmt.executeUpdate();

            if (success > 0) {
                // Retrieve the generated keys
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int refundID = generatedKeys.getInt(1);
                        return new Refund(
                            refundID,
                            getBooking(bookingID).getBookingId(),
                            refundAmount,
                            refundDate,
                            "pending"
                        );
                    } else {
                        throw new SQLException("Creating refund failed, no refund ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating refund failed, no rows affected.");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
