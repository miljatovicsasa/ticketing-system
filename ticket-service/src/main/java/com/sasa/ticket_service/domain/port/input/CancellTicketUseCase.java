package com.sasa.ticket_service.domain.port.input;

import com.sasa.ticket_service.domain.model.Ticket;

import java.util.UUID;

public interface CancellTicketUseCase {
    Ticket cancellTicket(UUID ticketId);
}
