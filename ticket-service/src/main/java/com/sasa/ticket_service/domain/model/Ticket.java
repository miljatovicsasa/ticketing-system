package com.sasa.ticket_service.domain.model;

import com.sasa.ticket_service.adapter.input.security.principal.AuthPrincipal;
import com.sasa.ticket_service.port.output.EventServicePort;

import java.time.OffsetDateTime;

public class Ticket {
    private Long id;
    private Long eventId;
    private Long userId;
    private int quantity;
    private TicketStatus status;
    private OffsetDateTime purchaseTime;
    private OffsetDateTime cancelTime;

    public Ticket(Long id, Long eventId, Long userId, int quantity,
                  TicketStatus status, OffsetDateTime purchaseTime, OffsetDateTime cancelTime) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.quantity = quantity;
        this.status = status;
        this.purchaseTime = purchaseTime;
        this.cancelTime = cancelTime;
    }

    public Ticket(Long id, Long eventId, int quantity,
                  TicketStatus status, OffsetDateTime purchaseTime, OffsetDateTime cancelTime) {
        this.id = id;
        this.eventId = eventId;
        this.quantity = quantity;
        this.status = status;
        this.purchaseTime = purchaseTime;
        this.cancelTime = cancelTime;
    }

    public void purchase(EventServicePort eventService, AuthPrincipal principal) {
        boolean reserved;
        try {
            reserved = eventService.eventCheckAndReserve(this.eventId, this.quantity, principal);
        } catch (Exception e) {
            throw new RuntimeException("eventCheckAndReserve on event service call failed", e);
        }

        if (!reserved) {
            throw new RuntimeException("Not enough tickets or exceeds max per purchase");
        }

        this.userId = principal.user().id();
        this.purchaseTime = OffsetDateTime.now();
        this.status = TicketStatus.ACTIVE;
    }


    public void cancel(EventServicePort eventService, AuthPrincipal principal) {
        if (this.status == TicketStatus.CANCELED) {
            throw new IllegalStateException("Ticket is already cancelled");
        }

        boolean cancelled;
        try {
            cancelled = eventService.eventCancel(this.eventId, this.quantity, principal );
        } catch (Exception e) {
            throw new RuntimeException("eventCancel on event service call failed", e);
        }

        if (!cancelled) {
            throw new RuntimeException("Not enough tickets or exceeds max per purchase");
        }

        this.status = TicketStatus.CANCELED;
        this.cancelTime = OffsetDateTime.now();
    }

    public void rollbackPurchase(EventServicePort eventService, AuthPrincipal principal) {
        try{
            eventService.rollbackReservation(this.eventId, this.quantity, principal);
        } catch (Exception e) {
            throw new RuntimeException("rollbackReservation on event service call failed", e);
        }
        this.status = null;
        this.userId = null;
        this.purchaseTime = null;
    }

    public void rollbackCancellation(EventServicePort eventService, AuthPrincipal principal) {

       try{
           eventService.rollbackCancellation(this.eventId, this.quantity, principal);
        } catch (Exception e) {
            throw new RuntimeException("rollbackCancellation on event service call failed", e);
        }
        this.status = TicketStatus.ACTIVE;
        this.cancelTime = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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
