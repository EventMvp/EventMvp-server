package com.eventhive.eventHive.Events.Service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.eventhive.eventHive.Category.Service.CategoryService;
import com.eventhive.eventHive.EventTicket.Dto.EventTicketDto;
import com.eventhive.eventHive.EventTicket.Entity.EventTicket;
import com.eventhive.eventHive.EventTicket.Service.EventTicketService;
import com.eventhive.eventHive.Events.Dto.CreateEventReqDto;
import com.eventhive.eventHive.Events.Dto.CreateEventResponseDto;
import com.eventhive.eventHive.Events.Dto.GetEventRespDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final Cloudinary cloudinary;

    public EventsServiceImpl(EventsRepository repository, CategoryService categoryService, UsersService usersService, EventTicketService eventTicketService, TicketTypeService ticketTypeService, VoucherService voucherService, Cloudinary cloudinary) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.usersService = usersService;
        this.eventTicketService = eventTicketService;
        this.ticketTypeService = ticketTypeService;
        this.voucherService = voucherService;
        this.cloudinary = cloudinary;
    }

    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList("image/jpeg", "image/png");

    private boolean isImageFile(MultipartFile file) {
        return ALLOWED_FILE_TYPES.contains(file.getContentType());
    }

    private String uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
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
    public CreateEventResponseDto createEvent(CreateEventReqDto dto, Long organizerId) throws IOException {
        Events event = new Events();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setDate(dto.getDate());
        event.setTime(dto.getTime());
        event.setLocation(dto.getLocation());
        event.setCategory(categoryService.getCategoryById(dto.getCategoryId()));
        event.setOrganizer(usersService.getUserById(organizerId));
        var savedEvent = repository.save(event);
        // Validate and upload the picture
        if (dto.getPicture() != null && !dto.getPicture().isEmpty()) {
            if (!isImageFile(dto.getPicture())) {
                throw new IllegalArgumentException("File must be an image (JPEG, PNG, GIF)");
            }
            String fileUrl = uploadFile(dto.getPicture());
            event.setPicture(fileUrl);
        }

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
            log.info(eventTicket.toString());
            eventTicketService.saveEventTicket(eventTicket);
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
        return CreateEventResponseDto.toEntity(savedEvent);
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
