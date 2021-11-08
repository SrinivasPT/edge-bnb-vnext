package com.edge.bnb.command.handler;


import com.edge.bnb.command.aggregate.Booking;
import com.edge.bnb.model.command.AddBookingCommand;
import com.edge.bnb.model.mappers.BookingMapper;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.Repository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AddBookingCommandHandler {
    @Autowired
    // Note: Following repository is not Jpa repository but the axon repository
    private Repository<Booking> repository;

    // Note: Command Handler is added here and removed from Aggregate
    @CommandHandler
    void handle(AddBookingCommand command) throws Exception {
        final BookingMapper mapper = Mappers.getMapper(BookingMapper.class);

        // Note: As this is new instance of the Booking, we are calling the newInstance method. For existing call load
        repository.newInstance(() -> mapper.toAggregate(command))
                .execute(booking -> booking.handle(command));
    }
}
