package com.restaurant.dao;

import com.restaurant.model.Table;
import java.util.List;

public interface TableDAO {
    List<Table> getAvailableTables();
    boolean bookTable(int tableId);
}
