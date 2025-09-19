package com.sasa.event_service.domain.exception;


public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(Long eventId) {
        super("Event with ID " + eventId + " not found.");
    }

    public EventNotFoundException(String message) {
        super(message);
    }
}
