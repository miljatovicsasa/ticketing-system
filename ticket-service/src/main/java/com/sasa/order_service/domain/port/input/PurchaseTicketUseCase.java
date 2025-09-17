package com.sasa.order_service.domain.port.input;

import com.sasa.order_service.domain.model.Ticket;

public interface PurchaseTicketUseCase {
    Ticket purchaseTicket(Ticket ticket);
}
