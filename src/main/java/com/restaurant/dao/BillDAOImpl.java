package com.restaurant.dao;

import com.restaurant.config.DBConnection;
import com.restaurant.model.Bill;
import java.sql.*;

public class BillDAOImpl implements BillDAO {
    @Override
    public int generateBill(Bill bill) {
        int billId = -1;
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO bills(order_id, total_amount) VALUES (?, ?) RETURNING bill_id")) {
            ps.setInt(1, bill.getOrderId());
            ps.setDouble(2, bill.getTotalAmount());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                billId = rs.getInt("bill_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billId;
    }

    @Override
    public boolean recordPayment(int billId) {
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE bills SET payment_status = 'Paid', paid_at = CURRENT_TIMESTAMP WHERE bill_id = ?")) {
            ps.setInt(1, billId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
