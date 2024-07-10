package com.eventhive.eventHive.Events.Service.Impl;

import com.eventhive.eventHive.Auth.helper.Claims;
import com.eventhive.eventHive.Category.Service.CategoryService;
import com.eventhive.eventHive.EventTicket.Dto.EventTicketDto;
import com.eventhive.eventHive.EventTicket.Entity.EventTicket;
import com.eventhive.eventHive.EventTicket.Service.EventTicketService;
import com.eventhive.eventHive.Events.Dto.CreateEventReqDto;
import com.eventhive.eventHive.Events.Dto.CreateEventResponseDto;
import com.eventhive.eventHive.Events.Dto.GetEventRespDto;
import com.eventhive.eventHive.Events.Entity.EventSpecifications;
import com.eventhive.eventHive.Events.Entity.Events;
import com.eventhive.eventHive.Events.Repository.EventsRepository;
import com.eventhive.eventHive.Events.Service.EventsService;
import com.eventhive.eventHive.TicketType.Entity.TicketType;
import com.eventhive.eventHive.TicketType.Service.TicketTypeService;
import com.eventhive.eventHive.Users.Service.UsersService;
import com.eventhive.eventHive.Voucher.Entity.Voucher;
import com.eventhive.eventHive.Voucher.Service.VoucherService;
import com.eventhive.eventHive.Voucher.dto.VoucherDto;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log
public class EventsServiceImpl implements EventsService {
    private final EventsRepository repository;
    private final CategoryService categoryService;
    private final UsersService usersService;
    private final EventTicketService eventTicketService;
    private final TicketTypeService ticketTypeService;
    private final VoucherService voucherService;
    public EventsServiceImpl(EventsRepository repository, CategoryService categoryService, UsersService usersService, EventTicketService eventTicketService, TicketTypeService ticketTypeService, VoucherService voucherService) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.usersService = usersService;
        this.eventTicketService = eventTicketService;
        this.ticketTypeService = ticketTypeService;
        this.voucherService = voucherService;
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
    public CreateEventResponseDto createEvent(CreateEventReqDto dto) {
        var claims = Claims.getClaimsFromJwt();
        Long organizerId = (Long) claims.get("userId");

        Events event = new Events();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setDate(dto.getDate());
        event.setTime(dto.getTime());
        event.setLocation(dto.getLocation());
        event.setCategory(categoryService.getCategoryById(dto.getCategoryId()));
        event.setOrganizer(usersService.getUserById(organizerId));
        event.setPicture(dto.getPicture());
        var savedEvent = repository.save(event);
        List<EventTicket> savedEventTickets = new ArrayList<>();
        //save event ticket
        for (EventTicketDto eventTicketDto : dto.getTicketTypes()){
            EventTicket eventTicket = new EventTicket();
            eventTicket.setEvent(savedEvent);
            //Fetch TicketType
            TicketType ticketType = ticketTypeService.getOrCreateTicketType(eventTicketDto.getTicketType());
            eventTicket.setTicketType(ticketType);
            eventTicket.setTicketTypeStr(ticketType.getTypeName());

            eventTicket.setAvailableSeats(eventTicketDto.getAvailableSeats());
            eventTicket.setPrice(eventTicketDto.getPrice());
            eventTicketService.saveEventTicket(eventTicket);
            savedEventTickets.add(eventTicket);
        }

        //Save the vouchers
        if (dto.getVouchers() != null){
            for (VoucherDto voucherDto : dto.getVouchers()){
                Voucher voucher = new Voucher();
                voucher.setEvent(savedEvent);
                voucher.setName(voucherDto.getName());
                voucher.setDiscountPercentage(voucherDto.getDiscountPercentage());
                voucher.setExpiryDate(voucherDto.getExpiryDate());
                voucher.setOrganizer(event.getOrganizer());
                voucherService.createVoucher(voucher);
            }
        }

        //Create Response DTO
        savedEvent.setEventTickets(savedEventTickets);
        return CreateEventResponseDto.toEntity(savedEvent);
    }

    @Override
    public List<GetEventRespDto> findEvents(Long categoryId, LocalDate startDate, LocalDate endDate, Boolean isFree, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Events> spec = EventSpecifications.withFilters(categoryId, startDate, endDate, isFree);

        Page<Events> eventsPage = repository.findAll(spec, pageable);

        return eventsPage.stream().map(GetEventRespDto::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<GetEventRespDto> searchEventByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Events> eventsPage = repository.findByTitle(title, pageable);

        return eventsPage.stream().map(GetEventRespDto::convertToDto).collect(Collectors.toList());
    }
}
