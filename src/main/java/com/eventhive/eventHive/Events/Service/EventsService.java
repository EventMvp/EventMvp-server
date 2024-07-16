package com.eventhive.eventHive.Events.Service;

import com.eventhive.eventHive.Events.Dto.CreateEventReqDto;
import com.eventhive.eventHive.Events.Dto.CreateEventResponseDto;
import com.eventhive.eventHive.Events.Dto.GetEventRespDto;
import com.eventhive.eventHive.Events.Entity.Events;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EventsService {
    List<GetEventRespDto> getAllEvents(int page, int size);
    GetEventRespDto getEventById(Long eventId);
    CreateEventResponseDto createEvent(CreateEventReqDto dto);
    Map<String, Object> findEvents(Long categoryId, LocalDate startDate, LocalDate endDate, Boolean isFree, int page, int size);
    List<GetEventRespDto> searchEventByTitle(String title, int page, int size);
    Events findById(Long eventId);
}
