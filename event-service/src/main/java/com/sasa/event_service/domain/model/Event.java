package com.sasa.event_service.domain.model;

import com.sasa.event_service.adapter.output.entity.EventEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Event {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private int capacity;
    private int remaining;
    private int maxPerPurchase;
    private EventStatus status;

    public Event(UUID id, String name, LocalDateTime dateTime, int capacity, int remaining, int maxPerPurchase, EventStatus status) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.remaining = remaining;
        this.maxPerPurchase = maxPerPurchase;
        this.status = status;
    }

    public static Event createNew(String name, LocalDateTime dateTime, int capacity, int maxPerPurchase) {
        if (dateTime == null) {
            throw new IllegalArgumentException("Event dateTime must not be null");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        if (maxPerPurchase <= 0) {
            throw new IllegalArgumentException("Max per purchase must be positive");
        }
        return new Event(
                UUID.randomUUID(),
                name,
                dateTime,
                capacity,
                capacity,
                maxPerPurchase,
                EventStatus.ACTIVE
        );
    }

    public static Event fromEntity(EventEntity entity) {
        return new Event(
                entity.getId(),
                entity.getName(),
                entity.getDateTime(),
                entity.getCapacity(),
                entity.getRemaining(),
                entity.getMaxPerPurchase(),
                EventStatus.valueOf(entity.getStatus())
        );
    }

    public void validatePurchase(int quantity) {
        if (quantity > maxPerPurchase) {
            throw new IllegalArgumentException("Cannot purchase more than " + maxPerPurchase + " tickets per request.");
        }
        if (quantity > remaining) {
            throw new IllegalStateException("Not enough tickets remaining.");
        }
    }

    public void purchase(int quantity) {
        validatePurchase(quantity);
        this.remaining -= quantity;
    }

    public void cancel(int quantity) {
        this.remaining += quantity;
        if (this.remaining > this.capacity) {
            this.remaining = this.capacity;
        }
    }

    public void rollbackPurchase(int quantity) {
        cancel(quantity);
    }

    public void rollbackCancel(int quantity) {
        purchase(quantity);
    }

    public void checkAndClose(LocalDateTime now) {
        if (dateTime.isBefore(now)) {
            this.status = EventStatus.FINISHED;
        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", capacity=" + capacity +
                ", remaining=" + remaining +
                ", maxPerPurchase=" + maxPerPurchase +
                ", status=" + status +
                '}';
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public int getMaxPerPurchase() {
        return maxPerPurchase;
    }

    public void setMaxPerPurchase(int maxPerPurchase) {
        this.maxPerPurchase = maxPerPurchase;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
