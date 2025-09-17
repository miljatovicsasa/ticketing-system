package com.sasa.event_service.domain.port.input;

public interface createEventUseCase {
    Event createEvent(CreateEventCommand cmd);
}
