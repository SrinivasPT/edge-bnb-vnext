package com.edge.bnb.command.lookupeventhandler;

import com.edge.bnb.command.repository.BookingLookupRepository;
import com.edge.bnb.model.event.BookingCreatedEvent;
import com.edge.bnb.model.lookup.BookingLookupEntity;
import com.edge.bnb.model.mappers.BookingMapper;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@ProcessingGroup("booking-group")
public class BookingLookupEventHandler {
    private final BookingLookupRepository bookingLookupRepository;

    public BookingLookupEventHandler(BookingLookupRepository bookingLookupRepository) {
        this.bookingLookupRepository = bookingLookupRepository;
    }

    @EventHandler
    public void on(BookingCreatedEvent event) {
        final BookingMapper mapper = Mappers.getMapper(BookingMapper.class);
        BookingLookupEntity bookingLookupEntity = mapper.toLookupEntity(event);
        bookingLookupRepository.save(bookingLookupEntity);
    }
}
