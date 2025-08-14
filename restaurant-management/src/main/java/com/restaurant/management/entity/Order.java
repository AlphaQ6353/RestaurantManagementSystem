package com.restaurant.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Table number is required")
    private Integer tableNumber;

    @NotBlank(message = "Items are required")
    private String items;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.PLACED;

    @ManyToOne
    @JoinColumn(name = "table_booking_id")
    @ToString.Exclude // Prevent circular reference in toString
    private TableBooking tableBooking;

    public enum Status {
        PLACED, IN_KITCHEN, SERVED
    }
}
