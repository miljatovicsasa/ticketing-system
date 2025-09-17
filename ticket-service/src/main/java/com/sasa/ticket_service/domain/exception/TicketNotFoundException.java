package com.sasa.ticket_service.domain.exception;

import java.util.UUID;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(UUID ticketId) {
        super("Ticket not found with id: " + ticketId);
    }
}
