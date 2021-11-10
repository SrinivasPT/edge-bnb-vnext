package com.edge.bnb.model.mappers;

import com.edge.bnb.command.aggregate.Booking;
import com.edge.bnb.model.command.AddBookingCommand;
import com.edge.bnb.model.entity.BookingEntity;
import com.edge.bnb.model.event.BookingCreatedEvent;
import com.edge.bnb.model.lookup.BookingLookupEntity;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface BookingMapper {
    BookingCreatedEvent toEvent(AddBookingCommand command);

    Booking toAggregate(AddBookingCommand command);

    void toAggregate(BookingCreatedEvent event, @MappingTarget Booking booking);

    BookingEntity toEntity(BookingCreatedEvent event);

    BookingLookupEntity toLookupEntity(BookingCreatedEvent event);
}
