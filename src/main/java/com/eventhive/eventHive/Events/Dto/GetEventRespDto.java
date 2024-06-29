package com.eventhive.eventHive.Events.Dto;

import com.eventhive.eventHive.Category.Dto.CategoryRespDto;
import com.eventhive.eventHive.EventTicket.Dto.EventTicketDto;
import com.eventhive.eventHive.Events.Entity.Events;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetEventRespDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private CategoryRespDto category;
    private List<EventTicketDto> eventTickets;

    public static GetEventRespDto convertToDto (Events events){
        GetEventRespDto dto = new GetEventRespDto();
        dto.setId(events.getId());
        dto.setTitle(events.getTitle());
        dto.setDescription(events.getDescription());
        dto.setDate(events.getDate());
        dto.setTime(events.getTime());
        dto.setLocation(events.getLocation());
        dto.setCategory(CategoryRespDto.convertDto(events.getCategory()));
        dto.setEventTickets(events.getEventTickets().stream()
                .map(EventTicketDto::convertToDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
