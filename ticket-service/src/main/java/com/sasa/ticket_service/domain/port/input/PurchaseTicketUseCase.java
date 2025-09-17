package com.sasa.ticket_service.domain.port.input;

import com.sasa.ticket_service.domain.model.Ticket;

public interface PurchaseTicketUseCase {
    Ticket purchaseTicket(Ticket ticket);
}
