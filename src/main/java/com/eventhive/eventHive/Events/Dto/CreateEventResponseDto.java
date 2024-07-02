package com.eventhive.eventHive.Events.Dto;

import com.eventhive.eventHive.Events.Entity.Events;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateEventResponseDto {
    private Long id;
    private String title;
    private String description;
    private String picture;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private String categoryName;
    private boolean freeEvent;

    public static CreateEventResponseDto toEntity (Events savedEvent){
        CreateEventResponseDto responseDto = new CreateEventResponseDto();
        responseDto.setId(savedEvent.getId());
        responseDto.setTitle(savedEvent.getTitle());
        responseDto.setDescription(savedEvent.getDescription());
        responseDto.setPicture(savedEvent.getPicture());
        responseDto.setDate(savedEvent.getDate());
        responseDto.setTime(savedEvent.getTime());
        responseDto.setLocation(savedEvent.getLocation());
        responseDto.setCategoryName(savedEvent.getCategory().getName());
        responseDto.setFreeEvent(savedEvent.isFreeEvent());

        return responseDto;
    }
}
