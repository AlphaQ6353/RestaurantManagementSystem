package com.restaurant.management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.restaurant.management.service.OrderService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class RestaurantManagementApplicationTests {

	@Autowired
	private OrderService orderService;

	@Test
	void contextLoads() {
		// Test that the context loads successfully
	}

	@Test
	void orderServiceBeanExists() {
		assertNotNull(orderService, "OrderService bean should be created");
	}
}