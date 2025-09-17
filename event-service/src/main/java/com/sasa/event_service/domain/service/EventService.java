package com.sasa.event_service.domain.service;

import com.sasa.event_service.domain.model.Event;
import com.sasa.event_service.domain.port.input.CancelTicketUseCase;
import com.sasa.event_service.domain.port.input.CreateEventUseCase;
import com.sasa.event_service.domain.port.input.GetAllEventsUseCase;
import com.sasa.event_service.domain.port.input.PurchaseTicketUseCase;
import com.sasa.event_service.domain.port.output.EventRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EventService implements PurchaseTicketUseCase, CreateEventUseCase, GetAllEventsUseCase, CancelTicketUseCase {

    private final EventRepositoryPort eventRepositoryPort;

    public EventService(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Transactional
    @Override
    public void purchaseTickets(UUID eventId, int quantity) {
        Event event = eventRepositoryPort.getEvent(eventId);
        event.purchase(quantity);
        eventRepositoryPort.saveEvent(event);
    };

    @Override
    public Event createEvent(Event event) {
        return eventRepositoryPort.saveEvent(event);
    };

    @Override
    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepositoryPort.listEvents(pageable);
    }

    @Transactional
    public void markFinishedEvents() {
        List<Event> ongoingEvents = eventRepositoryPort.findAllOngoingEvents();
        LocalDateTime now = LocalDateTime.now();
        for (Event event : ongoingEvents) {
            event.checkAndClose(now);
            eventRepositoryPort.saveEvent(event);
        }
    }

    @Transactional
    @Override
    public void cancelTicket(UUID eventId, int quantity) {
        Event event = eventRepositoryPort.getEvent(eventId);
        event.cancel(quantity);
        eventRepositoryPort.saveEvent(event);
    }
}
