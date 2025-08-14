package com.restaurant.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "table_bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotNull(message = "Booking time is required")
    private LocalDateTime bookingTime;

    @NotNull(message = "Table number is required")
    private Integer tableNumber;

    @NotNull(message = "Number of guests is required")
    private Integer numberOfGuests;

    @OneToMany(mappedBy = "tableBooking", cascade = CascadeType.ALL)
    @ToString.Exclude // Prevent circular reference in toString
    private List<Order> orders;
}
