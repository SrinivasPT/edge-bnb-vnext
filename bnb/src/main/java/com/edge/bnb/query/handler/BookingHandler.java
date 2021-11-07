package com.edge.bnb.query.handler;

import com.edge.bnb.model.event.BookingCreatedEvent;
import com.edge.bnb.model.mappers.BookingMapper;
import com.edge.bnb.query.repository.BookingEntityRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("booking")
public class BookingHandler {
    private final BookingEntityRepository repository;

    public BookingHandler(BookingEntityRepository repository){
        this.repository = repository;
    }

    @EventHandler
    public void on(BookingCreatedEvent event){
        final BookingMapper mapper = Mappers.getMapper(BookingMapper.class );
        repository.save(mapper.toEntity(event));
    }
}
