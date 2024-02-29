package data;

// import java.util.List;
// import java.util.Map;

public class TicketOption {
    private int totalAvailable;
    private int priceMultipler;
    private String name;

    public TicketOption(int totalAvailable, int priceMultipler, String name) {
        this.totalAvailable = totalAvailable;
        this.priceMultipler = priceMultipler;
        this.name = name;
    }

    public int getTotalAvailable() {
        return totalAvailable;
    }

    public int getPriceMultipler() {
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
