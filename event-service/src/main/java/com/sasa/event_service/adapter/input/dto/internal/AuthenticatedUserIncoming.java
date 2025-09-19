package com.sasa.event_service.adapter.input.dto.internal;

public record AuthenticatedUserIncoming(
        Long id, String username, String email,String firstName, String lastName, String role
) {}
