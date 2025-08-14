// src/main/java/com/restaurant/management/repository/OrderRepository.java
package com.restaurant.management.repository;

import com.restaurant.management.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}