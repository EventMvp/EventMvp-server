package com.eventhive.eventHive.Events.Controller;

import com.eventhive.eventHive.Events.Dto.CreateEventReqDto;
import com.eventhive.eventHive.Events.Service.EventsService;
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
        return ResponseEntity.ok(service.getAllEvents());
    }

    @PostMapping("/add-event")
    public ResponseEntity<?> createEvent(@RequestBody CreateEventReqDto dto){
        return ResponseEntity.ok(service.createEvent(dto, 1L));
    }
}
