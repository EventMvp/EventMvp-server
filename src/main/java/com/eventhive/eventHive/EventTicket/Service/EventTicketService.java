package com.eventhive.eventHive.EventTicket.Service;

import com.eventhive.eventHive.EventTicket.Entity.EventTicket;

public interface EventTicketService {
    EventTicket getEventTicketById (Long id);

    void saveEventTicket(EventTicket eventTicket);
}
