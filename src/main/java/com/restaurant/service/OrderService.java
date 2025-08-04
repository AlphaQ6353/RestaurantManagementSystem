package com.restaurant.service;

import com.restaurant.dao.OrderDAO;
import com.restaurant.dao.OrderDAOImpl;
import com.restaurant.model.Order;

public class OrderService {
    private final OrderDAO dao = new OrderDAOImpl();

    public int placeOrder(Order order) {
        return dao.createOrder(order);
    }

    public boolean prepareOrder(int orderId) {
        return dao.markOrderAsPrepared(orderId);
    }
}
