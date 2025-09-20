package com.sasa.event_service.adapter.output.entity;

import com.sasa.event_service.domain.model.EventStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "events", schema = "event")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private OffsetDateTime dateTime;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int remaining;

    @Column(nullable = false)
    private int maxPerPurchase;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    protected EventEntity() {}

    public EventEntity(Long id, String name, OffsetDateTime dateTime, int capacity, int remaining, int maxPerPurchase, EventStatus status) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.remaining = remaining;
        this.maxPerPurchase = maxPerPurchase;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public OffsetDateTime getDateTime() { return dateTime; }
    public int getCapacity() { return capacity; }
    public int getRemaining() { return remaining; }
    public int getMaxPerPurchase() { return maxPerPurchase; }
    public EventStatus getStatus() { return status; }

}
