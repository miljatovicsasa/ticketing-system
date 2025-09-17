package com.sasa.event_service.adapter.output.entity;

import com.sasa.event_service.domain.model.EventStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events", schema = "event")
public class EventEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int remaining;

    @Column(nullable = false)
    private int maxPerPurchase;

    @Column(nullable = false)
    private String status;

    protected EventEntity() {}

    public EventEntity(UUID id, String name, LocalDateTime dateTime, int capacity, int remaining, int maxPerPurchase, String status) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.remaining = remaining;
        this.maxPerPurchase = maxPerPurchase;
        this.status = status;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getDateTime() { return dateTime; }
    public int getCapacity() { return capacity; }
    public int getRemaining() { return remaining; }
    public int getMaxPerPurchase() { return maxPerPurchase; }
    public String getStatus() { return status; }

    public void setRemaining(int remaining) { this.remaining = remaining; }
    public void setStatus(String status) { this.status = status; }
}
