package com.restaurant.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.management.entity.TableBooking;
import com.restaurant.management.service.TableBookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TableBookingController.class)
class TableBookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TableBookingService tableBookingService;

    @Autowired
    private ObjectMapper objectMapper;

    private TableBooking testBooking;

    @BeforeEach
    void setUp() {
        testBooking = new TableBooking();
        testBooking.setId(1L);
        testBooking.setCustomerName("Alice Smith");
        testBooking.setBookingTime(LocalDateTime.of(2024, 1, 15, 19, 0));
        testBooking.setTableNumber(5);
        testBooking.setNumberOfGuests(4);
    }

    @Test
    @WithMockUser
    void testCreateBooking_Success() throws Exception {
        when(tableBookingService.createBooking(any(TableBooking.class))).thenReturn(testBooking);

        mockMvc.perform(post("/bookings")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBooking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerName").value("Alice Smith"))
                .andExpect(jsonPath("$.tableNumber").value(5))
                .andExpect(jsonPath("$.numberOfGuests").value(4));
    }

    @Test
    @WithMockUser
    void testCreateBooking_InvalidInput() throws Exception {
        TableBooking invalidBooking = new TableBooking();
        invalidBooking.setCustomerName("");
        invalidBooking.setBookingTime(LocalDateTime.now());
        invalidBooking.setTableNumber(5);
        invalidBooking.setNumberOfGuests(4);

        mockMvc.perform(post("/bookings")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBooking)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testGetAllBookings_Success() throws Exception {
        TableBooking booking2 = new TableBooking();
        booking2.setId(2L);
        booking2.setCustomerName("Bob Johnson");
        booking2.setBookingTime(LocalDateTime.of(2024, 1, 16, 20, 0));
        booking2.setTableNumber(3);
        booking2.setNumberOfGuests(2);

        List<TableBooking> bookings = Arrays.asList(testBooking, booking2);
        when(tableBookingService.getAllBookings()).thenReturn(bookings);

        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].customerName").value("Alice Smith"))
                .andExpect(jsonPath("$[1].customerName").value("Bob Johnson"));
    }

    @Test
    @WithMockUser
    void testGetBookingById_Success() throws Exception {
        when(tableBookingService.getBookingById(1L)).thenReturn(Optional.of(testBooking));

        mockMvc.perform(get("/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerName").value("Alice Smith"));
    }

    @Test
    @WithMockUser
    void testGetBookingById_NotFound() throws Exception {
        when(tableBookingService.getBookingById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/bookings/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testUpdateBooking_Success() throws Exception {
        TableBooking updatedBooking = new TableBooking();
        updatedBooking.setId(1L);
        updatedBooking.setCustomerName("Alice Updated");
        updatedBooking.setBookingTime(LocalDateTime.of(2024, 1, 17, 19, 30));
        updatedBooking.setTableNumber(7);
        updatedBooking.setNumberOfGuests(6);

        when(tableBookingService.updateBooking(eq(1L), any(TableBooking.class))).thenReturn(updatedBooking);

        mockMvc.perform(put("/bookings/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBooking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Alice Updated"))
                .andExpect(jsonPath("$.tableNumber").value(7));
    }
}
