package com.sasa.ticket_service.domain.port.input;

import com.sasa.ticket_service.domain.model.Ticket;

public interface CancellTicketUseCase {
    Ticket cancellTicket(long ticketId);
}
