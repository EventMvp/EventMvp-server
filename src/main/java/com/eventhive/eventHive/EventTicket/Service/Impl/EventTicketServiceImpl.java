package com.eventhive.eventHive.EventTicket.Service.Impl;

import com.eventhive.eventHive.EventTicket.Dto.EventTicketDto;
import com.eventhive.eventHive.EventTicket.Entity.EventTicket;
import com.eventhive.eventHive.EventTicket.Repository.EventTicketRepository;
import com.eventhive.eventHive.EventTicket.Service.EventTicketService;
import com.eventhive.eventHive.Exceptions.EventTicketNotExistException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log
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
    public List<EventTicketDto> getTicketEventByEventId(Long eventId) {
        Optional<List<EventTicket>> optionalEventTicketList= repository.findEventTicketByEventId(eventId);
        if (optionalEventTicketList.isEmpty()){
            throw new EventTicketNotExistException("Event ticket is not exist");
        }
        List<EventTicket> eventTicketList = optionalEventTicketList.get();

        return eventTicketList.stream()
                .map(EventTicketDto::convertToDto)
                .toList();
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
