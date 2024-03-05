package controller;

import java.util.List;
import java.util.Map;

public class EventController {
    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Get all events method
    public List<Event> getEvents() {
        return eventService.getEvents();
    }

    // Get events managed by a specific EventManager
    public List<Event> getMyManagedEvents(EventManager eventManager) {
        return eventService.getMyManagedEvents(eventManager);
    }

    // Cancel an event method
    public String cancelEvent(Event event) {
        return eventService.cancelEvent(event);
    }

    // Update event details method
    public Event updateEvent(Event event, EventManager eventManager, Map<String, Object> details) {
        return eventService.updateEvent(event, eventManager, details);
    }

    // Create a new event method
    public Event createEvent(EventManager eventManager, Map<String, Object> details) {
        return eventService.createEvent(eventManager, details);
    }

    // Add officers to an event method
    public Map<String, Boolean> addOfficer(EventManager eventManager, List<String> userIds) {
        return eventService.addOfficer(eventManager, userIds);
    }
}
