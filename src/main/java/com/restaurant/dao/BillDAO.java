package com.restaurant.dao;

import com.restaurant.model.Bill;

public interface BillDAO {
    int generateBill(Bill bill);
    boolean recordPayment(int billId);
}
