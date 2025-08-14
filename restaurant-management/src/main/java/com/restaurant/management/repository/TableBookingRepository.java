// src/main/java/com/restaurant/management/repository/TableBookingRepository.java
package com.restaurant.management.repository;

import com.restaurant.management.entity.TableBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableBookingRepository extends JpaRepository<TableBooking, Long> {
}