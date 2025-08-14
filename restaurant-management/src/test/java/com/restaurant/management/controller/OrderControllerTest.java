package com.restaurant.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.management.entity.Order;
import com.restaurant.management.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = Order.builder()
                .id(1L)
                .tableNumber(5)
                .items("Pizza, Pasta, Coke")
                .status(Order.Status.PLACED)
                .build();
    }

    @Test
    @WithMockUser
    void testCreateOrder_Success() throws Exception {
        // Given
        when(orderService.createOrder(any(Order.class))).thenReturn(testOrder);

        // When & Then
        mockMvc.perform(post("/orders")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.tableNumber").value(5))
                .andExpect(jsonPath("$.items").value("Pizza, Pasta, Coke"))
                .andExpect(jsonPath("$.status").value("PLACED"));
    }

    @Test
    @WithMockUser
    void testCreateOrder_InvalidInput() throws Exception {
        // Given - Order with missing items
        Order invalidOrder = Order.builder()
                .tableNumber(5)
                .items("")
                .status(Order.Status.PLACED)
                .build();

        // When & Then
        mockMvc.perform(post("/orders")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOrder)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testGetAllOrders_Success() throws Exception {
        // Given
        Order order2 = Order.builder()
                .id(2L)
                .tableNumber(3)
                .items("Burger, Fries")
                .status(Order.Status.IN_KITCHEN)
                .build();
        List<Order> orders = Arrays.asList(testOrder, order2);
        when(orderService.getAllOrders()).thenReturn(orders);

        // When & Then
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].items").value("Pizza, Pasta, Coke"))
                .andExpect(jsonPath("$[1].items").value("Burger, Fries"));
    }

    @Test
    @WithMockUser
    void testUpdateOrderStatus_Success() throws Exception {
        // Given
        Order updatedOrder = Order.builder()
                .id(1L)
                .tableNumber(5)
                .items("Pizza, Pasta, Coke")
                .status(Order.Status.SERVED)
                .build();
        when(orderService.updateOrderStatus(eq(1L), eq(Order.Status.SERVED))).thenReturn(updatedOrder);

        // When & Then
        mockMvc.perform(put("/orders/1")
                        .with(csrf())
                        .param("status", "SERVED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SERVED"));
    }

    @Test
    @WithMockUser
    void testGetAllOrders_EmptyList() throws Exception {
        // Given
        when(orderService.getAllOrders()).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
