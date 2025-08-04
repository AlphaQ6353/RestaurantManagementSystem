package com.restaurant.model;

import java.util.List;

public class Order {
    private int orderId;
    private int tableId;
    private String status;
    private List<OrderItem> items;

    public Order(int tableId, List<OrderItem> items) {
        this.tableId = tableId;
        this.items = items;
        this.status = "Pending";
    }

    public int getTableId() { return tableId; }
    public List<OrderItem> getItems() { return items; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
}
