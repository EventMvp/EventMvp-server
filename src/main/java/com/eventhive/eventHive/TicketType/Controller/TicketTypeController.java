package com.eventhive.eventHive.TicketType.Controller;

import com.eventhive.eventHive.Response.Response;
import com.eventhive.eventHive.TicketType.Entity.TicketType;
import com.eventhive.eventHive.TicketType.Service.TicketTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ticket-type")
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;

    public TicketTypeController(TicketTypeService ticketTypeService) {
        this.ticketTypeService = ticketTypeService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllTicketType(){
        return Response.successResponse("Get All Ticket Type", ticketTypeService.getAllTicket());
    }

    @PostMapping()
    public ResponseEntity<?> createTicketType(@RequestBody String typeName){
        return  Response.successResponse("Ticket type is added", ticketTypeService.createTicketType(typeName));
    }
}
