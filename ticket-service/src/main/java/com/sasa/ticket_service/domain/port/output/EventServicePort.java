package com.sasa.ticket_service.domain.port.output;

public interface EventServicePort {
    boolean canPurchase(Long eventId, int quantity);
}
