package com.sasa.ticket_service.adapter.input.dto.request;

import jakarta.validation.constraints.NotNull;

public record EventPurchaseAndCancelRequestDto(
        @NotNull Long eventId,
        @NotNull int quantity
){}
