package user;  

import java.util.List;

public class EventManager extends User{
    private List<Event> managedEvents;

    public EventManager(int id, String password, String email, String type, List<Event> managedEvents) {
        super(id, password, email, type);
        this.managedEvents = managedEvents;
    }

    public List<Event> getManagedEvents() {
        return managedEvents;
    }
    
}
