package com.restaurant.model;

public class Bill {
    private int billId;
    private int orderId;
    private double totalAmount;
    private String paymentStatus;

    public Bill(int orderId, double totalAmount) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.paymentStatus = "Unpaid";
    }

    public int getOrderId() { return orderId; }
    public double getTotalAmount() { return totalAmount; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}
