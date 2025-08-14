package com.restaurant.management.service;

import com.restaurant.management.entity.Order;
import com.restaurant.management.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setTableNumber(5);
        testOrder.setItems("Pizza, Pasta, Coke");
        testOrder.setStatus(Order.Status.PLACED);
    }

    @Test
    void testCreateOrder_Success() {
        // Given
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        // When
        Order result = orderService.createOrder(testOrder);

        // Then
        assertNotNull(result);
        assertEquals(Integer.valueOf(5), result.getTableNumber());
        assertEquals("Pizza, Pasta, Coke", result.getItems());
        assertEquals(Order.Status.PLACED, result.getStatus());
        verify(orderRepository).save(testOrder);
    }

    @Test
    void testGetAllOrders_Success() {
        // Given
        Order order2 = new Order();
        order2.setId(2L);
        order2.setTableNumber(3);
        order2.setItems("Burger, Fries");
        order2.setStatus(Order.Status.IN_KITCHEN);

        List<Order> expectedOrders = Arrays.asList(testOrder, order2);
        when(orderRepository.findAll()).thenReturn(expectedOrders);

        // When
        List<Order> result = orderService.getAllOrders();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedOrders, result);
        verify(orderRepository).findAll();
    }

    @Test
    void testUpdateOrderStatus_Success() {
        // Given
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        // When
        Order result = orderService.updateOrderStatus(1L, Order.Status.SERVED);

        // Then
        assertNotNull(result);
        assertEquals(Order.Status.SERVED, testOrder.getStatus());
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(testOrder);
    }

    @Test
    void testUpdateOrderStatus_OrderNotFound() {
        // Given
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> orderService.updateOrderStatus(99L, Order.Status.SERVED));
        assertEquals("Order not found with id: 99", exception.getMessage());
        verify(orderRepository).findById(99L);
        verify(orderRepository, never()).save(any());
    }
}
