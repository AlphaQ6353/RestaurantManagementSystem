// src/main/java/com/restaurant/management/controller/TableBookingController.java
package com.restaurant.management.controller;

import com.restaurant.management.entity.TableBooking;
import com.restaurant.management.service.TableBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class TableBookingController {

    @Autowired
    private TableBookingService tableBookingService;

    @PostMapping
    public ResponseEntity<TableBooking> createBooking(@Valid @RequestBody TableBooking booking) {
        TableBooking createdBooking = tableBookingService.createBooking(booking);
        return ResponseEntity.ok(createdBooking);
    }

    @GetMapping
    public ResponseEntity<List<TableBooking>> getAllBookings() {
        List<TableBooking> bookings = tableBookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableBooking> getBookingById(@PathVariable Long id) {
        return tableBookingService.getBookingById(id)
                .map(booking -> ResponseEntity.ok().body(booking))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TableBooking> updateBooking(@PathVariable Long id,
                                                      @Valid @RequestBody TableBooking bookingDetails) {
        TableBooking updatedBooking = tableBookingService.updateBooking(id, bookingDetails);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        tableBookingService.deleteBooking(id);
        return ResponseEntity.ok().build();
    }
}