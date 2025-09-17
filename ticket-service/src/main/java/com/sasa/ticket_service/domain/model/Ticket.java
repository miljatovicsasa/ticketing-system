package com.sasa.ticket_service.domain.model;

import java.time.LocalDateTime;

public class Ticket {
    private Long id;
    private Long eventId;
    private Long userId;
    private int quantity;
    private TicketStatus status;
    private LocalDateTime purchaseTime;
    private LocalDateTime cancelTime;

    public Ticket(Long id, Long eventId, Long userId, int quantity,
                  TicketStatus status, LocalDateTime purchaseTime, LocalDateTime cancelTime) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.quantity = quantity;
        this.status = status;
        this.purchaseTime = purchaseTime;
        this.cancelTime = cancelTime;
    }

    public Ticket(Long id, Long eventId, int quantity,
                  TicketStatus status, LocalDateTime purchaseTime, LocalDateTime cancelTime) {
        this.id = id;
        this.eventId = eventId;
        this.quantity = quantity;
        this.status = status;
        this.purchaseTime = purchaseTime;
        this.cancelTime = cancelTime;
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
