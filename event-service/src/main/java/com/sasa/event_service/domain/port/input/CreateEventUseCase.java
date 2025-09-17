package com.sasa.event_service.domain.port.input;

import com.sasa.event_service.domain.model.Event;

public interface CreateEventUseCase {
    Event createEvent(Event event);
}
