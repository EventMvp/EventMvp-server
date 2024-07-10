package com.eventhive.eventHive.Events.Service;

import com.eventhive.eventHive.Events.Dto.CreateEventReqDto;
import com.eventhive.eventHive.Events.Dto.CreateEventResponseDto;
import com.eventhive.eventHive.Events.Dto.GetEventRespDto;

import java.time.LocalDate;
import java.util.List;

public interface EventsService {
    List<GetEventRespDto> getAllEvents(int page, int size);
    GetEventRespDto getEventById(Long eventId);
    CreateEventResponseDto createEvent(CreateEventReqDto dto);
    List<GetEventRespDto> findEvents(Long categoryId, LocalDate startDate, LocalDate endDate, Boolean isFree, int page, int size);
    List<GetEventRespDto> searchEventByTitle(String title, int page, int size);
}
