package data;

import java.time;
import java.util.Map;

public class Refund {
    private int refundId;
    private int bookingId;
    private double refundAmount;
    private LocalDateTime refundDate;
    private String refundStatus;

    public Refund(int refundId, int bookingId, double refundAmount, LocalDateTime refundDate, String refundStatus) {
        this.refundId = refundId;
        this.bookingId = bookingId;
        this.refundAmount = refundAmount;
        this.refundDate = refundDate;
        this.refundStatus = refundStatus;
    }

    // Getters
    public int getRefundId() {
        return refundId;
    }

    public int getBookingId() {
        return bookingId;
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
    public Map<String, float> getRefundDetails() {
        return null;
    }
}
