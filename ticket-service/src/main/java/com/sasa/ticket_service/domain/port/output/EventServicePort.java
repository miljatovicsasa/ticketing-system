package com.sasa.ticket_service.domain.port.output;

import java.util.UUID;

public interface EventServicePort {
    boolean eventCheckAndReserve(UUID eventId, int quantity);
    void rollbackReservation(UUID eventId, int quantity);

    boolean eventCancel(UUID eventId, int quantity);
    void rollbackCancellation(UUID eventId, int quantity);
}
