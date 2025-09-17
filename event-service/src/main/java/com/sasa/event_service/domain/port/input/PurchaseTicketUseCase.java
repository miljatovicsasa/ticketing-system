package com.sasa.event_service.domain.port.input;

import java.util.UUID;

public interface PurchaseTicketUseCase {
    void purchaseTickets(UUID eventId, int quantity);
}
