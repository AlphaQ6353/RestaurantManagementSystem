package com.restaurant.service;

import com.restaurant.dao.BillDAO;
import com.restaurant.dao.BillDAOImpl;
import com.restaurant.model.Bill;

public class BillService {
    private final BillDAO dao = new BillDAOImpl();

    public int generateBill(Bill bill) {
        return dao.generateBill(bill);
    }

    public boolean recordPayment(int billId) {
        return dao.recordPayment(billId);
    }
}
