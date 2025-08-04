package com.restaurant.service;

import com.restaurant.dao.TableDAO;
import com.restaurant.dao.TableDAOImpl;
import com.restaurant.model.Table;
import java.util.List;

public class TableService {
    private final TableDAO dao = new TableDAOImpl();

    public List<Table> getAvailableTables() {
        return dao.getAvailableTables();
    }

    public boolean bookTable(int tableId) {
        return dao.bookTable(tableId);
    }
}
