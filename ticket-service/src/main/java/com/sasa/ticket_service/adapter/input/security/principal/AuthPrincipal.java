package com.sasa.ticket_service.adapter.input.security.principal;

import com.sasa.ticket_service.adapter.input.dto.internal.AuthenticatedUserIncoming;

public record AuthPrincipal(
        AuthenticatedUserIncoming user,
        String token
) {}
