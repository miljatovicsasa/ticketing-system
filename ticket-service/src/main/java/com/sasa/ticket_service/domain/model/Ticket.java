package com.sasa.ticket_service.domain.model;

import com.sasa.ticket_service.port.output.EventServicePort;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Ticket {
    private UUID id;
    private UUID eventId;
    private UUID userId;
    private int quantity;
    private TicketStatus status;
    private OffsetDateTime purchaseTime;
    private OffsetDateTime cancelTime;

    public Ticket(UUID id, UUID eventId, UUID userId, int quantity,
                  TicketStatus status, OffsetDateTime purchaseTime, OffsetDateTime cancelTime) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.quantity = quantity;
        this.status = status;
        this.purchaseTime = purchaseTime;
        this.cancelTime = cancelTime;
    }

    public Ticket(UUID id, UUID eventId, int quantity,
                  TicketStatus status, OffsetDateTime purchaseTime, OffsetDateTime cancelTime) {
        this.id = id;
        this.eventId = eventId;
        this.quantity = quantity;
        this.status = status;
        this.purchaseTime = purchaseTime;
        this.cancelTime = cancelTime;
    }

    public void purchase(EventServicePort eventService) {
        boolean reserved = eventService.eventCheckAndReserve(this.eventId, this.quantity);
        if (!reserved) {
            throw new RuntimeException("Not enough tickets or exceeds max per purchase");
        }

        this.userId = UUID.randomUUID();
        this.purchaseTime = OffsetDateTime.now();
        this.status = TicketStatus.ACTIVE;
    }

    public void cancel(EventServicePort eventService) {
        if (this.status == TicketStatus.CANCELED) {
            throw new IllegalStateException("Ticket is already cancelled");
        }

        boolean cancelled = eventService.eventCancel(this.eventId, this.quantity);
        if (!cancelled) {
            throw new RuntimeException("Not enough tickets or exceeds max per purchase");
        }

        this.status = TicketStatus.CANCELED;
        this.cancelTime = OffsetDateTime.now();
    }

    public void rollbackPurchase(EventServicePort eventService) {
        eventService.rollbackReservation(this.eventId, this.quantity);
        this.status = null;
        this.userId = null;
        this.purchaseTime = null;
    }

    public void rollbackCancellation(EventServicePort eventService) {
        eventService.rollbackCancellation(this.eventId, this.quantity);
        this.status = TicketStatus.ACTIVE;
        this.cancelTime = null;
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

    public OffsetDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(OffsetDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public OffsetDateTime getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(OffsetDateTime cancelTime) {
        this.cancelTime = cancelTime;
    }
}
