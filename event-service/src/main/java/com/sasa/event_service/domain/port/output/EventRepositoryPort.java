package com.sasa.event_service.domain.port.output;

import com.sasa.event_service.domain.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface EventRepositoryPort {
    Event getEvent(UUID id);
    Page<Event> listEvents(Pageable pageable);
    Event saveEvent(Event event);
    List<Event> findAllOngoingEvents();

}
