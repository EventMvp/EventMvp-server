package com.eventhive.eventHive.Events.Service;

import com.eventhive.eventHive.Events.Dto.CreateEventReqDto;
import com.eventhive.eventHive.Events.Dto.GetAllEventRespDto;
import com.eventhive.eventHive.Events.Entity.Events;

import java.util.List;

public interface EventsService {
    List<GetAllEventRespDto> getAllEvents();

    Events createEvent(CreateEventReqDto dto, Long organizerId);
}
