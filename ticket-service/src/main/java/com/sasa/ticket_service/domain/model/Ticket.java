package com.sasa.ticket_service.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private UUID id;
    private UUID eventId;
    private UUID userId;
    private int quantity;
    private TicketStatus status;
    private LocalDateTime purchaseTime;
    private LocalDateTime cancelTime;

    public Ticket(UUID id, UUID eventId, UUID userId, int quantity,
                  TicketStatus status, LocalDateTime purchaseTime, LocalDateTime cancelTime) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.quantity = quantity;
        this.status = status;
        this.purchaseTime = purchaseTime;
        this.cancelTime = cancelTime;
    }

    public Ticket(UUID id, UUID eventId, int quantity,
                  TicketStatus status, LocalDateTime purchaseTime, LocalDateTime cancelTime) {
        this.id = id;
        this.eventId = eventId;
        this.quantity = quantity;
        this.status = status;
        this.purchaseTime = purchaseTime;
        this.cancelTime = cancelTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public LocalDateTime getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(LocalDateTime cancelTime) {
        this.cancelTime = cancelTime;
    }
}
