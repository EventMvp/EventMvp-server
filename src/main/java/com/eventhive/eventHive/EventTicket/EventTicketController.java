package com.eventhive.eventHive.EventTicket;

import com.eventhive.eventHive.EventTicket.Dto.EventTicketDto;
import com.eventhive.eventHive.EventTicket.Service.EventTicketService;
import com.eventhive.eventHive.Response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
public class EventTicketController {
    private final EventTicketService service;

    public EventTicketController(EventTicketService service) {
        this.service = service;
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<?> getTicketTypeByEVentId(@PathVariable Long eventId){
        return Response.successResponse("Get Ticket Type for event: " + eventId, service.getTicketEventByEventId(eventId));
    }
}
