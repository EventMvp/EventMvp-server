package com.eventhive.eventHive.EventTicket.Dto;

import com.eventhive.eventHive.EventTicket.Entity.EventTicket;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EventTicketDto {
    private Long id;
    private String ticketType;
    private int availableSeats;
    private BigDecimal price;

    public static EventTicketDto convertToDto(EventTicket eventTicket){
        EventTicketDto dto = new EventTicketDto();
        dto.setId(eventTicket.getId());
        dto.setTicketType(eventTicket.getTicketType().getTypeName());
        dto.setAvailableSeats(eventTicket.getAvailableSeats());
        dto.setPrice(eventTicket.getPrice());
        return dto;
    }
}
