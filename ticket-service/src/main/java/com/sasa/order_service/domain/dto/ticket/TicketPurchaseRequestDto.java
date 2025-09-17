package com.sasa.order_service.domain.dto.ticket;

import jakarta.validation.constraints.NotNull;

public class TicketPurchaseRequestDto {

    @NotNull(message = "eventId is required")
    private Long eventId;

    @NotNull(message = "quantity is required")
    private int quantity;

    public TicketPurchaseRequestDto() {}

    public TicketPurchaseRequestDto(Long eventId, int quantity) {
        this.eventId = eventId;
        this.quantity = quantity;
    }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
