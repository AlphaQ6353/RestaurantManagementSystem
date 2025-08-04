package com.restaurant.dao;

import com.restaurant.config.DBConnection;
import com.restaurant.model.Table;
import java.sql.*;
import java.util.*;

public class TableDAOImpl implements TableDAO {
    @Override
    public List<Table> getAvailableTables() {
        List<Table> tables = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tables WHERE is_booked = FALSE")) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tables.add(new Table(
                        rs.getInt("table_id"),
                        rs.getInt("capacity"),
                        rs.getBoolean("is_booked")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    @Override
    public boolean bookTable(int tableId) {
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE tables SET is_booked = TRUE WHERE table_id = ?")) {

            stmt.setInt(1, tableId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
