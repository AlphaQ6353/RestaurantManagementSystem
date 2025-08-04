package com.restaurant.model;

public class Table {
    private int tableId;
    private int capacity;
    private boolean isBooked;

    public Table(int tableId, int capacity, boolean isBooked) {
        this.tableId = tableId;
        this.capacity = capacity;
        this.isBooked = isBooked;
    }

    public int getTableId() { return tableId; }
    public int getCapacity() { return capacity; }
    public boolean isBooked() { return isBooked; }
    public void setBooked(boolean booked) { isBooked = booked; }
}
