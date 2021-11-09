package com.edge.bnb.command.repository;

import com.edge.bnb.model.lookup.BookingLookupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingLookupRepository extends JpaRepository<BookingLookupEntity, UUID> {
}
