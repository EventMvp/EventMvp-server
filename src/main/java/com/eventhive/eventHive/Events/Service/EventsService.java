package com.eventhive.eventHive.Events.Service;

import com.eventhive.eventHive.Events.Dto.CreateEventReqDto;
import com.eventhive.eventHive.Events.Dto.GetEventRespDto;
import com.eventhive.eventHive.Events.Entity.Events;

import java.time.LocalDate;
import java.util.List;

public interface EventsService {
    List<GetEventRespDto> getAllEvents(int page, int size);
    GetEventRespDto getEventById(Long eventId);
    Events createEvent(CreateEventReqDto dto, Long organizerId);
    List<GetEventRespDto> filterWithMultipleCriteria(Long categoryId, LocalDate date, Boolean isFree, int page, int size);
    List<GetEventRespDto> searchEventByTitle(String title, int page, int size);
}
