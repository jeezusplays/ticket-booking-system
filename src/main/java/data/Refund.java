package data;

import java.time;
import java.util.Map;

public class Refund {
    private int refundID;
    private int bookingID;
    private double refundAmount;
    private LocalDateTime refundDate;
    private String refundStatus;

    public Refund(int refundID, int bookingID, double refundAmount, LocalDateTime refundDate, String refundStatus) {
        this.refundID = refundID;
        this.bookingID = bookingID;
        this.refundAmount = refundAmount;
        this.refundDate = refundDate;
        this.refundStatus = refundStatus;
    }

    // Getters
    public int getrefundID() {
        return refundID;
    }

    public int getbookingID() {
        return bookingID;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public LocalDateTime getRefundDate() {
        return refundDate;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    // Setters
    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public void setRefundDate(LocalDateTime refundDate) {
        this.refundDate = refundDate;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    // Other methods
    public Map<String, Object> getRefundDetails() {
        return null;
    }
}
