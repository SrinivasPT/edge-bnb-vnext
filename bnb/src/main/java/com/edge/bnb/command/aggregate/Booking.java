package com.edge.bnb.command.aggregate;

import com.edge.bnb.model.command.AddBookingCommand;
import com.edge.bnb.model.event.BookingCreatedEvent;
import com.edge.bnb.model.mappers.BookingMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j @Data @Aggregate
public class Booking {

    @AggregateIdentifier
    private UUID bookingId;
    private UUID hotelId;
    private UUID customerId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    public Booking(){ }

    // Note: @EventHandler annotation is removed from here as a separate command handler is created.
    public void handle (AddBookingCommand command){
        final BookingMapper mapper = Mappers.getMapper(BookingMapper.class );
        log.info("BookingAggregate::AddBookingCommand: In the Command Handler ".concat(command.toString()));
        apply(mapper.toEvent(command));
    }

    @EventSourcingHandler
    protected void on(BookingCreatedEvent event){
        final BookingMapper mapper = Mappers.getMapper(BookingMapper.class );
        log.info("BookingAggregate::BookingCreatedEventHandler: ".concat(event.toString()));
        mapper.toAggregate(event, this);
    }
}
