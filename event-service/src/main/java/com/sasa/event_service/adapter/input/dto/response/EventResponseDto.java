package com.sasa.event_service.adapter.input.dto.response;

import com.sasa.event_service.domain.model.Event;

import java.time.OffsetDateTime;
import java.util.UUID;

public record EventResponseDto(
        UUID id,
        String name,
        OffsetDateTime dateTime,
        int capacity,
        int remaining,
        int maxPerPurchase,
        String status
) {

    public static EventResponseDto from(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event must not be null");
        }
        return new EventResponseDto(
                event.getId(),
                event.getName(),
                event.getDateTime(),
                event.getCapacity(),
                event.getRemaining(),
                event.getMaxPerPurchase(),
                event.getStatus().name()
        );
    }
}
