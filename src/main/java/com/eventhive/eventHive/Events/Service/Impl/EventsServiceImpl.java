package com.eventhive.eventHive.Events.Service.Impl;

import com.eventhive.eventHive.Category.Service.CategoryService;
import com.eventhive.eventHive.EventTicket.Dto.EventTicketDto;
import com.eventhive.eventHive.EventTicket.Entity.EventTicket;
import com.eventhive.eventHive.EventTicket.Service.EventTicketService;
import com.eventhive.eventHive.Events.Dto.CreateEventReqDto;
import com.eventhive.eventHive.Events.Entity.Events;
import com.eventhive.eventHive.Events.Entity.TicketType;
import com.eventhive.eventHive.Events.Repository.EventsRepository;
import com.eventhive.eventHive.Events.Service.EventsService;
import com.eventhive.eventHive.Users.Service.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventsServiceImpl implements EventsService {
    private final EventsRepository repository;
    private final CategoryService categoryService;
    private final UsersService usersService;
    private final EventTicketService eventTicketService;



    public EventsServiceImpl(EventsRepository repository, CategoryService categoryService, UsersService usersService, EventTicketService eventTicketService) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.usersService = usersService;
        this.eventTicketService = eventTicketService;
    }

    @Override
    public List<Events> getAllEvents() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Events createEvent(CreateEventReqDto dto, Long organizerId) {
        Events event = new Events();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setDate(dto.getDate());
        event.setTime(dto.getTime());
        event.setLocation(dto.getLocation());
        event.setPrice(dto.getPrice());

        event.setCategory(categoryService.getCategoryById(dto.getCategoryId()));
        event.setOrganizer(usersService.getUserById(organizerId));

        //Save the event
        Events savedEvent = repository.save(event);

        //save event ticket
        for (EventTicketDto eventTicketDto : dto.getTicketTypes()){
            EventTicket eventTicket = new EventTicket();
            eventTicket.setEvent(savedEvent);
            eventTicket.setTicketType(TicketType.valueOf(eventTicketDto.getTicketType()));
            eventTicket.setAvailableSeats(eventTicketDto.getAvailableSeats());
            eventTicketService.saveEventTicket(eventTicket);

        }

        return savedEvent;
    }
}
