package com.sasa.event_service.adapter.input.security.principal;


import com.sasa.event_service.adapter.input.dto.internal.AuthenticatedUserIncoming;

public record AuthPrincipal(
        AuthenticatedUserIncoming user,
        String token
) {}
