package com.restaurant.model;

public class OrderItem {
    private int itemId;
    private int quantity;

    public OrderItem(int itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public int getItemId() { return itemId; }
    public int getQuantity() { return quantity; }
}