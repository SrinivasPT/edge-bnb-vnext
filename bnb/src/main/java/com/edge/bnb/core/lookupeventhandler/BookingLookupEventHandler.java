package com.edge.bnb.core.lookupeventhandler;

import com.edge.bnb.core.repository.BookingLookupRepository;
import com.edge.bnb.model.event.BookingCreatedEvent;
import com.edge.bnb.model.lookup.BookingLookupEntity;
import com.edge.bnb.model.mappers.BookingMapper;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("booking-group")
public class BookingLookupEventHandler {

    private final BookingLookupRepository bookingLookupRepository;
    final BookingMapper mapper = Mappers.getMapper(BookingMapper.class);

    public BookingLookupEventHandler(BookingLookupRepository bookingLookupRepository) {
        this.bookingLookupRepository = bookingLookupRepository;
    }

    @EventHandler
    public void on(BookingCreatedEvent event) {
        BookingLookupEntity bookingLookupEntity = mapper.toLookupEntity(event);
        bookingLookupRepository.save(bookingLookupEntity);
    }
}
