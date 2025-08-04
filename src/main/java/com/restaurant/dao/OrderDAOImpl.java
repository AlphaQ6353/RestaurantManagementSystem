package com.restaurant.dao;

import com.restaurant.config.DBConnection;
import com.restaurant.model.Order;
import com.restaurant.model.OrderItem;
import java.sql.*;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public int createOrder(Order order) {
        int orderId = -1;
        try (Connection conn = DBConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO orders(table_id) VALUES (?) RETURNING order_id");
            ps.setInt(1, order.getTableId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                orderId = rs.getInt("order_id");
                order.setOrderId(orderId);
            }
            for (OrderItem item : order.getItems()) {
                PreparedStatement psItem = conn.prepareStatement(
                        "INSERT INTO order_items(order_id, item_id, quantity) VALUES (?, ?, ?)"
                );
                psItem.setInt(1, orderId);
                psItem.setInt(2, item.getItemId());
                psItem.setInt(3, item.getQuantity());
                psItem.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    @Override
    public boolean markOrderAsPrepared(int orderId) {
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE orders SET status = 'Prepared' WHERE order_id = ?")) {
            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
