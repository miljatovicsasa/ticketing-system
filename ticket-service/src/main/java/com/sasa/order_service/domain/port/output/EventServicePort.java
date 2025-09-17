package com.sasa.order_service.domain.port.output;

public interface EventServicePort {
    boolean canPurchase(Long eventId, int quantity);
}
