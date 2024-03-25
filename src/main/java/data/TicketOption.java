package data;

// import java.util.List;
// import java.util.Map;

public class TicketOption {
    private int ticketOptionID;
    private int totalAvailable;
    private int priceMultipler;
    private String name;

    public TicketOption(int ticketOptionID, int totalAvailable, int priceMultipler, String name) {
        this.ticketOptionID = ticketOptionID;
        this.totalAvailable = totalAvailable;
        this.priceMultipler = priceMultipler;
        this.name = name;
    }

    public int getTicketOptionID() {
        return ticketOptionID;
    }

    public int getTotalAvailable() {
        return totalAvailable;
    }

    public int getPriceMultiplier() {
        return priceMultipler;
    }

    public String getName() {
        return name;
    }

    // private Map<String, float> options;

    // public TicketOption(Map<String, float> options) {
    //     this.options = options;
    // }

    // public List<String> getOptions() {
    //     return null;
    // }

    // public float getPrice() {
    //     return null;
    // }
}
