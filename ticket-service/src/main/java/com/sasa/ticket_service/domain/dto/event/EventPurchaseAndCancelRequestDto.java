package com.sasa.ticket_service.domain.dto.event;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EventPurchaseAndCancelRequestDto(
        @NotNull UUID eventId,
        @NotNull int quantity
){}
