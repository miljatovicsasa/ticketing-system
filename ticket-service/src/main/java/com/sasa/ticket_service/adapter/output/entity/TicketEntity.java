package com.sasa.ticket_service.adapter.output.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets", schema = "ticket")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false)
    private UUID eventId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private OffsetDateTime purchaseTime;

    private OffsetDateTime cancelTime;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public TicketEntity() {}

    public TicketEntity(UUID id, UUID eventId, UUID userId, int quantity, String status) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.quantity = quantity;
        this.status = status;
        this.purchaseTime = OffsetDateTime.now();
        this.cancelTime = null;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public OffsetDateTime getPurchaseTime() { return purchaseTime; }
    public void setPurchaseTime(OffsetDateTime purchaseTime) { this.purchaseTime = purchaseTime; }

    public OffsetDateTime getCancelTime() { return cancelTime; }
    public void setCancelTime(OffsetDateTime cancelTime) { this.cancelTime = cancelTime; }
}
