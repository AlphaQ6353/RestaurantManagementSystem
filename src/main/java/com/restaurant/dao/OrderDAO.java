package com.restaurant.dao;

import com.restaurant.model.Order;

public interface OrderDAO {
    int createOrder(Order order);
    boolean markOrderAsPrepared(int orderId);
}
