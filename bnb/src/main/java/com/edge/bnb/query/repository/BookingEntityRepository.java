package com.edge.bnb.query.repository;

import com.edge.bnb.model.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingEntityRepository extends JpaRepository<BookingEntity, UUID> {
}
