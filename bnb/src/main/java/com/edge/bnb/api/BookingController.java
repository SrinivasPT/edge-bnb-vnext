package com.edge.bnb.api;

import com.edge.bnb.model.command.AddBookingCommand;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.Future;

@Slf4j
@RestController
public class BookingController {
    private final CommandGateway commandGateway;

    public BookingController(@SuppressWarnings("SpringJavaAutowiringInspection") CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @PostMapping("/booking")
    public Future<String> createBooking(@RequestBody @Valid AddBookingCommand command){
        log.info("Booking Controller: Received Command: ".concat(command.toString()));
        UUID bookingId = command.getBookingId() == null ? UUID.randomUUID() : command.getBookingId();
        command.setBookingId(bookingId);
        return commandGateway.send(command);
    }
}
