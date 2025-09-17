package com.sasa.event_service.domain.exception;

import java.util.UUID;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(UUID eventId) {
        super("Event with ID " + eventId + " not found.");
    }

    public EventNotFoundException(String message) {
        super(message);
    }
}
