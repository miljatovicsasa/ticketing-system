package com.sasa.order_service.domain.port.input;

import com.sasa.order_service.domain.model.Ticket;

public interface CancellTicketUseCase {
    Ticket cancellTicket(long ticketId);
}
