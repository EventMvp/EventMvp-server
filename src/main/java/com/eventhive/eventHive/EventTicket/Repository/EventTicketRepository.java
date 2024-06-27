package com.eventhive.eventHive.EventTicket.Repository;

import com.eventhive.eventHive.EventTicket.Entity.EventTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTicketRepository extends JpaRepository<EventTicket, Long> {
}
