package com.restaurant.management;

import com.restaurant.management.entity.Order;
import com.restaurant.management.repository.OrderRepository;
import com.restaurant.management.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.profiles.active=test")
class RestaurantManagementApplicationTests {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@Test
	void contextLoads() {
		// Verify application context loads
	}

	@Test
	void orderServiceBeanExists() {
		assertThat(orderService).isNotNull();
	}

	@Test
	void saveOrder() {
		Order order = Order.builder()
				.tableNumber(5)
				.items("Pizza, Pasta")
				.status(Order.Status.PLACED)
				.build();
		Order savedOrder = orderRepository.save(order);
		assertThat(savedOrder.getId()).isNotNull();
		assertThat(savedOrder.getTableNumber()).isEqualTo(5);
		assertThat(savedOrder.getItems()).isEqualTo("Pizza, Pasta");
		assertThat(savedOrder.getStatus()).isEqualTo(Order.Status.PLACED);
	}
}