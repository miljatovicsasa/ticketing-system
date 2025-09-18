package com.sasa.event_service.port.input;

import com.sasa.event_service.domain.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EventUseCasePort {
    Page<Event> getAllEvents(Pageable pageable);
    Event createEvent(Event event);

    void purchaseTickets(UUID eventId, int quantity);
    void rollbackPurchaseTicket(UUID eventId, int quantity);
    void cancelTicket(UUID eventId, int quantity);
    void rollbackCancelTicket(UUID eventId, int quantity);
}
