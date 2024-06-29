package com.eventhive.eventHive.Events.Service;

import com.eventhive.eventHive.Events.Dto.CreateEventReqDto;
import com.eventhive.eventHive.Events.Dto.GetEventRespDto;
import com.eventhive.eventHive.Events.Entity.Events;

import java.util.List;

public interface EventsService {
    List<GetEventRespDto> getAllEvents();
    GetEventRespDto getEventById(Long eventId);
    Events createEvent(CreateEventReqDto dto, Long organizerId);


}
