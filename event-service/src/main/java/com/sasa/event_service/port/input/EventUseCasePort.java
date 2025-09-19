package com.sasa.event_service.port.input;

import com.sasa.event_service.domain.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventUseCasePort {
    Page<Event> getAllEvents(Pageable pageable);
    Event createEvent(Event event);

    void purchaseTickets(Long eventId, int quantity);
    void rollbackPurchaseTicket(Long eventId, int quantity);
    void cancelTicket(Long eventId, int quantity);
    void rollbackCancelTicket(Long eventId, int quantity);
}
