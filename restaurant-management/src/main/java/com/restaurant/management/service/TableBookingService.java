// src/main/java/com/restaurant/management/service/TableBookingService.java
package com.restaurant.management.service;

import com.restaurant.management.entity.TableBooking;
import com.restaurant.management.repository.TableBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TableBookingService {

    @Autowired
    private TableBookingRepository tableBookingRepository;

    public TableBooking createBooking(TableBooking booking) {
        return tableBookingRepository.save(booking);
    }

    public List<TableBooking> getAllBookings() {
        return tableBookingRepository.findAll();
    }

    public Optional<TableBooking> getBookingById(Long id) {
        return tableBookingRepository.findById(id);
    }

    public TableBooking updateBooking(Long id, TableBooking bookingDetails) {
        TableBooking booking = tableBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        booking.setCustomerName(bookingDetails.getCustomerName());
        booking.setBookingTime(bookingDetails.getBookingTime());
        booking.setTableNumber(bookingDetails.getTableNumber());
        booking.setNumberOfGuests(bookingDetails.getNumberOfGuests());

        return tableBookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        tableBookingRepository.deleteById(id);
    }
}