package com.eventhive.eventHive.Events.Controller;

import com.eventhive.eventHive.Events.Dto.CreateEventReqDto;
import com.eventhive.eventHive.Events.Service.EventsService;
import com.eventhive.eventHive.Response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/events")
public class EventsController {
    private final EventsService service;

    public EventsController(EventsService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<?> getAllEvents(){
        return Response.successResponse("Get events successfully", service.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById (@PathVariable("id") Long Id){
        return Response.successResponse("Event with ID: " + Id + "successfully fetch", service.getEventById(Id));
    }

    @PostMapping("/add-event")
    public ResponseEntity<?> createEvent(@RequestBody CreateEventReqDto dto){
        return Response.successResponse("Create event successfully", service.createEvent(dto, 2L));
    }
}
