package com.sasa.event_service.domain.port.input;

import com.sasa.event_service.domain.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetAllEventsUseCase {
    Page<Event> getAllEvents(Pageable pageable);
}
