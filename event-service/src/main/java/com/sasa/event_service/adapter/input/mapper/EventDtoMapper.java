package com.sasa.event_service.adapter.input.mapper;

import com.sasa.event_service.adapter.input.dto.request.EventCreateRequestDto;
import com.sasa.event_service.adapter.input.dto.response.EventResponseDto;
import com.sasa.event_service.domain.model.Event;

public class EventDtoMapper {

    public static Event requestToDomain(EventCreateRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("EventCreateRequestDto must not be null");
        }
        return Event.createNew(
                dto.name(),
                dto.dateTime(),
                dto.capacity(),
                dto.maxPerPurchase()
        );
    }

    public static EventResponseDto domainToResponseDto(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event must not be null");
        }
        return EventResponseDto.from(event);
    }
}
