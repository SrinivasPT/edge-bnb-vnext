package com.edge.bnb.model.event;

import com.edge.bnb.core.base.BaseEvent;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Setter
public class BookingCreatedEvent extends BaseEvent {
    @NotNull UUID bookingId;
    @NotNull UUID hotelId;
    @NotNull UUID customerId;
    @NotNull LocalDateTime fromDate;
    @NotNull LocalDateTime toDate;
}
