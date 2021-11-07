package com.edge.bnb.model.command;

import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data @Setter
public class AddBookingCommand {
    UUID bookingId;
    @NotNull UUID hotelId;
    @NotNull UUID customerId;
    @NotNull LocalDateTime fromDate;
    @NotNull LocalDateTime toDate;
}
