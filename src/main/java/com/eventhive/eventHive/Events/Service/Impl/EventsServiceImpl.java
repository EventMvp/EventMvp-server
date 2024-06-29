package com.eventhive.eventHive.Events.Service.Impl;

import com.eventhive.eventHive.Category.Service.CategoryService;
import com.eventhive.eventHive.EventTicket.Dto.EventTicketDto;
import com.eventhive.eventHive.EventTicket.Entity.EventTicket;
import com.eventhive.eventHive.EventTicket.Service.EventTicketService;
import com.eventhive.eventHive.Events.Dto.CreateEventReqDto;
import com.eventhive.eventHive.Events.Dto.GetEventRespDto;
import com.eventhive.eventHive.Events.Entity.Events;
import com.eventhive.eventHive.Events.Repository.EventsRepository;
import com.eventhive.eventHive.Events.Service.EventsService;
import com.eventhive.eventHive.TicketType.Entity.TicketType;
import com.eventhive.eventHive.TicketType.Service.TicketTypeService;
import com.eventhive.eventHive.Users.Service.UsersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventsServiceImpl implements EventsService {
    private final EventsRepository repository;
    private final CategoryService categoryService;
    private final UsersService usersService;
    private final EventTicketService eventTicketService;
    private final TicketTypeService ticketTypeService;

    public EventsServiceImpl(EventsRepository repository, CategoryService categoryService, UsersService usersService, EventTicketService eventTicketService, TicketTypeService ticketTypeService) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.usersService = usersService;
        this.eventTicketService = eventTicketService;
        this.ticketTypeService = ticketTypeService;
    }

    @Override
    public List<GetEventRespDto> getAllEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Events> getAllEvents = repository.findAll(pageable);

        return getAllEvents.stream().map(GetEventRespDto::convertToDto).collect(Collectors.toList());
    }

    @Override
    public GetEventRespDto getEventById(Long Id) {
        Events getCurrentEvent = repository.findById(Id).orElseThrow(() -> new RuntimeException("Event does not exist"));
        return GetEventRespDto.convertToDto(getCurrentEvent);
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
        event.setCategory(categoryService.getCategoryById(dto.getCategoryId()));
        event.setOrganizer(usersService.getUserById(organizerId));

        //Save the event
        Events savedEvent = repository.save(event);

        //save event ticket
        for (EventTicketDto eventTicketDto : dto.getTicketTypes()){
            EventTicket eventTicket = new EventTicket();
            eventTicket.setEvent(savedEvent);

            //Fetch TicketType
            TicketType ticketType = ticketTypeService.getOrCreateTicketType(eventTicketDto.getTicketType());
            eventTicket.setTicketType(ticketType);

            eventTicket.setAvailableSeats(eventTicketDto.getAvailableSeats());
            eventTicket.setPrice(eventTicketDto.getPrice());
            eventTicketService.saveEventTicket(eventTicket);
        }

        return savedEvent;
    }

    @Override
    public List<GetEventRespDto> filterWithMultipleCriteria(Long categoryId, LocalDate date, Boolean isFree, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Events> eventsPage = repository.findByFilters(categoryId, date, isFree, pageable);

        return eventsPage.stream().map(GetEventRespDto::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<GetEventRespDto> searchEventByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Events> eventsPage = repository.findByTitle(title, pageable);

        return eventsPage.stream().map(GetEventRespDto::convertToDto).collect(Collectors.toList());
    }
}
