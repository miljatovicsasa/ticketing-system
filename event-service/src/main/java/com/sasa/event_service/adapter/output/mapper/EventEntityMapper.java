package com.sasa.event_service.adapter.output.mapper;


import com.sasa.event_service.adapter.output.entity.EventEntity;
import com.sasa.event_service.domain.model.Event;

public class EventEntityMapper {

    public static EventEntity domainToEntity(Event event) {
        if (event == null) throw new IllegalArgumentException("Event must not be null");

        return new EventEntity(
                event.getId(),
                event.getName(),
                event.getDateTime(),
                event.getCapacity(),
                event.getRemaining(),
                event.getMaxPerPurchase(),
                event.getStatus().name()
        );
    }

    public static Event entityToDomain(EventEntity entity) {
        if (entity == null) throw new IllegalArgumentException("Entity must not be null");

        return Event.fromEntity(entity);
    }
}

