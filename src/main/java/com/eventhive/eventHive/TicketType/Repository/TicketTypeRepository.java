package com.eventhive.eventHive.TicketType.Repository;

import com.eventhive.eventHive.TicketType.Entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {
    Optional<TicketType> findByTypeName(String typeName);
}
