package com.eventhive.eventHive.EventTicket.Repository;

import com.eventhive.eventHive.EventTicket.Entity.EventTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventTicketRepository extends JpaRepository<EventTicket, Long> {
    Optional<List<EventTicket>> findEventTicketByEventId(Long eventId);
}
