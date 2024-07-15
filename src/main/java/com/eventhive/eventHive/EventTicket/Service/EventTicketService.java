package com.eventhive.eventHive.EventTicket.Service;

import com.eventhive.eventHive.EventTicket.Dto.EventTicketDto;
import com.eventhive.eventHive.EventTicket.Entity.EventTicket;

import java.util.List;

public interface EventTicketService {
    EventTicket getEventTicketById (Long id);
    List<EventTicketDto> getTicketEventByEventId (Long eventId);
    void saveEventTicket(EventTicket eventTicket);
    void reduceTicket(EventTicket eventTicket, int quantity);
}
