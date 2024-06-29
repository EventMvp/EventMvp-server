package com.eventhive.eventHive.TicketType.Service.Impl;

import com.eventhive.eventHive.TicketType.Entity.TicketType;
import com.eventhive.eventHive.TicketType.Repository.TicketTypeRepository;
import com.eventhive.eventHive.TicketType.Service.TicketTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketTypeImpl implements TicketTypeService {
    private final TicketTypeRepository repository;

    public TicketTypeImpl(TicketTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TicketType> getAllTicket() {
        return repository.findAll();
    }

    @Override
    public TicketType getOrCreateTicketType(String typeName) {
        return repository.findByTypeName(typeName).orElseGet(() -> createTicketType(typeName));
    }

    @Override
    public TicketType createTicketType(String typeName) {
        TicketType ticketType = new TicketType();
        ticketType.setTypeName(typeName);
        return repository.save(ticketType);
    }
}
