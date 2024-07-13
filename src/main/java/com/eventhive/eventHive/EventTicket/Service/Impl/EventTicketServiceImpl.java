package com.eventhive.eventHive.EventTicket.Service.Impl;

import com.eventhive.eventHive.EventTicket.Entity.EventTicket;
import com.eventhive.eventHive.EventTicket.Repository.EventTicketRepository;
import com.eventhive.eventHive.EventTicket.Service.EventTicketService;
import org.springframework.stereotype.Service;

@Service
public class EventTicketServiceImpl implements EventTicketService {

    private final EventTicketRepository repository;

    public EventTicketServiceImpl(EventTicketRepository repository) {
        this.repository = repository;
    }

    @Override
    public EventTicket getEventTicketById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Event Ticket not available"));
    }

    @Override
    public void saveEventTicket(EventTicket eventTicket) {
        repository.save(eventTicket);
    }

    @Override
    public void reduceTicket(EventTicket eventTicket, int quantity) {
        int newAvailableSeat = eventTicket.getAvailableSeats() - quantity;
        if (newAvailableSeat < 0){
            throw new RuntimeException("Not enough Tickets for " + eventTicket.getTicketType().getTypeName());
        }
        eventTicket.setAvailableSeats(newAvailableSeat);
    }

}
