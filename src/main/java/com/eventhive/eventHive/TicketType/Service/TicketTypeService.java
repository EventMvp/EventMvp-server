package com.eventhive.eventHive.TicketType.Service;

import com.eventhive.eventHive.TicketType.Entity.TicketType;

import java.util.List;

public interface TicketTypeService {
    List<TicketType> getAllTicket();

    TicketType getOrCreateTicketType(String typeName);

    TicketType createTicketType(String typeName);
}
