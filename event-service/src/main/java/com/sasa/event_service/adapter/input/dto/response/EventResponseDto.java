package com.sasa.event_service.adapter.input.dto.response;

import com.sasa.event_service.domain.model.Event;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;

@Schema(name = "EventResponse", description = "Response DTO for event details")
public record EventResponseDto(
        @Schema(description = "Unique identifier of the event", example = "1")
        Long id,

        @Schema(description = "Name of the event", example = "Summer Festival")
        String name,

        @Schema(description = "Date and time of the event", example = "2025-12-25T18:00:00+01:00")
        OffsetDateTime dateTime,

        @Schema(description = "Total capacity of the event", example = "100")
        int capacity,

        @Schema(description = "Remaining available tickets", example = "42")
        int remaining,

        @Schema(description = "Maximum tickets per purchase", example = "5")
        int maxPerPurchase,

        @Schema(description = "Status of the event", example = "ACTIVE")
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
