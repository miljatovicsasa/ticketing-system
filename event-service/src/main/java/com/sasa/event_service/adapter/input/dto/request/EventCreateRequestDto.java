package com.sasa.event_service.adapter.input.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Future;
import java.time.OffsetDateTime;

@Schema(name = "EventCreateRequest", description = "Request DTO for creating a new event")
public record EventCreateRequestDto(
        @Schema(description = "Name of the event", example = "Summer Festival")
        @NotNull(message = "Event name is required")
        String name,

        @Schema(description = "Date and time of the event (must be in the future)", example = "2025-12-25T18:00:00+01:00")
        @NotNull(message = "Event dateTime is required")
        @Future(message = "Event dateTime must be in the future")
        OffsetDateTime dateTime,

        @Schema(description = "Maximum capacity of the event", example = "100")
        @Min(value = 1, message = "Capacity must be at least 1")
        int capacity,

        @Schema(description = "Maximum tickets per purchase", example = "5")
        @Min(value = 1, message = "Max per purchase must be at least 1")
        int maxPerPurchase
) { }
