package com.restaurant.management.service;

import com.restaurant.management.entity.TableBooking;
import com.restaurant.management.repository.TableBookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TableBookingServiceTest {

    @Mock
    private TableBookingRepository tableBookingRepository;

    @InjectMocks
    private TableBookingService tableBookingService;

    private TableBooking testBooking;

    @BeforeEach
    void setUp() {
        testBooking = new TableBooking();
        testBooking.setId(1L);
        testBooking.setCustomerName("Alice Smith");
        testBooking.setBookingTime(LocalDateTime.now().plusDays(1));
        testBooking.setTableNumber(5);
        testBooking.setNumberOfGuests(4);
    }

    @Test
    void testCreateBooking_Success() {
        // Given
        when(tableBookingRepository.save(any(TableBooking.class))).thenReturn(testBooking);

        // When
        TableBooking result = tableBookingService.createBooking(testBooking);

        // Then
        assertNotNull(result);
        assertEquals("Alice Smith", result.getCustomerName());
        assertEquals(Integer.valueOf(5), result.getTableNumber());
        assertEquals(Integer.valueOf(4), result.getNumberOfGuests());
        verify(tableBookingRepository).save(testBooking);
    }

    @Test
    void testGetAllBookings_Success() {
        // Given
        TableBooking booking2 = new TableBooking();
        booking2.setId(2L);
        booking2.setCustomerName("Bob Johnson");
        booking2.setBookingTime(LocalDateTime.now().plusDays(2));
        booking2.setTableNumber(3);
        booking2.setNumberOfGuests(2);

        List<TableBooking> expectedBookings = Arrays.asList(testBooking, booking2);
        when(tableBookingRepository.findAll()).thenReturn(expectedBookings);

        // When
        List<TableBooking> result = tableBookingService.getAllBookings();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedBookings, result);
        verify(tableBookingRepository).findAll();
    }

    @Test
    void testGetBookingById_Success() {
        // Given
        when(tableBookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));

        // When
        Optional<TableBooking> result = tableBookingService.getBookingById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testBooking, result.get());
        verify(tableBookingRepository).findById(1L);
    }

    @Test
    void testUpdateBooking_Success() {
        // Given
        TableBooking updatedDetails = new TableBooking();
        updatedDetails.setCustomerName("Alice Updated");
        updatedDetails.setBookingTime(LocalDateTime.now().plusDays(3));
        updatedDetails.setTableNumber(7);
        updatedDetails.setNumberOfGuests(6);

        when(tableBookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));
        when(tableBookingRepository.save(any(TableBooking.class))).thenReturn(testBooking);

        // When
        TableBooking result = tableBookingService.updateBooking(1L, updatedDetails);

        // Then
        assertNotNull(result);
        verify(tableBookingRepository).findById(1L);
        verify(tableBookingRepository).save(testBooking);
    }
}