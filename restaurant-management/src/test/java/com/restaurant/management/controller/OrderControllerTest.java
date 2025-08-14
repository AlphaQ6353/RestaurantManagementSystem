package com.restaurant.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.management.entity.Order;
import com.restaurant.management.service.OrderService;
import com.restaurant.management.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @Mock
    private JwtUtil jwtUtil;

    private ObjectMapper objectMapper;

    @InjectMocks
    private OrderController orderController;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(orderController)
                .build();
        testOrder = Order.builder()
                .id(1L)
                .tableNumber(5)
                .items("Pizza, Pasta, Coke")
                .status(Order.Status.PLACED)
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateOrder_Success() throws Exception {
        when(orderService.createOrder(any(Order.class))).thenReturn(testOrder);
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
    @WithMockUser(roles = "ADMIN")
    void testCreateOrder_InvalidInput() throws Exception {
        Order invalidOrder = Order.builder()
                .tableNumber(5)
                .items("")
                .status(Order.Status.PLACED)
                .build();
        mockMvc.perform(post("/orders")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOrder)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllOrders_Success() throws Exception {
        Order order2 = Order.builder()
                .id(2L)
                .tableNumber(3)
                .items("Burger, Fries")
                .status(Order.Status.IN_KITCHEN)
                .build();
        List<Order> orders = Arrays.asList(testOrder, order2);
        when(orderService.getAllOrders()).thenReturn(orders);
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].items").value("Pizza, Pasta, Coke"))
                .andExpect(jsonPath("$[1].items").value("Burger, Fries"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateOrderStatus_Success() throws Exception {
        Order updatedOrder = Order.builder()
                .id(1L)
                .tableNumber(5)
                .items("Pizza, Pasta, Coke")
                .status(Order.Status.SERVED)
                .build();
        when(orderService.updateOrderStatus(eq(1L), eq(Order.Status.SERVED))).thenReturn(updatedOrder);
        mockMvc.perform(put("/orders/1")
                        .with(csrf())
                        .param("status", "SERVED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SERVED"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllOrders_EmptyList() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Arrays.asList());
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}