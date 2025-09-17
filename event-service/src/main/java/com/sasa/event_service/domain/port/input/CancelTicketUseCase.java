package com.sasa.event_service.domain.port.input;

import java.util.UUID;

public interface CancelTicketUseCase {
    void cancelTicket(UUID eventId, int quantity);
}
