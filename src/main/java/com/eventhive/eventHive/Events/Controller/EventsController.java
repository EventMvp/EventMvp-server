package com.eventhive.eventHive.Events.Controller;

import com.eventhive.eventHive.Events.Dto.CreateEventReqDto;
import com.eventhive.eventHive.Events.Dto.GetEventRespDto;
import com.eventhive.eventHive.Events.Service.EventsService;
import com.eventhive.eventHive.Response.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/events")
public class EventsController {
    private final EventsService service;

    public EventsController(EventsService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<?> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return Response.successResponse("Get events successfully", service.getAllEvents(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById (@PathVariable("id") Long Id){
        return Response.successResponse("Event with ID: " + Id + "successfully fetch", service.getEventById(Id));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterEventsByMultipleCriteria(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Boolean isFree,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        List<GetEventRespDto> events = service.filterWithMultipleCriteria(categoryId, date, isFree, page, size);
        return Response.successResponse("Events with criteria successfully fetch", events);
    }

    @PostMapping("/add-event")
    public ResponseEntity<?> createEvent(@RequestBody CreateEventReqDto dto){
        return Response.successResponse("Success Add Event", service.createEvent(dto, 2L));

    }

    @GetMapping("/search")
    public ResponseEntity<?> searchEventsByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        List<GetEventRespDto> events = service.searchEventByTitle(title, page, size);

        return Response.successResponse("Search result fetched successfully", events);
    }
}
