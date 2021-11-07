package com.edge.bnb.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class BookingEntity {
    @Id
    private UUID bookingId;
    private UUID hotelId;
    private UUID customerId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
