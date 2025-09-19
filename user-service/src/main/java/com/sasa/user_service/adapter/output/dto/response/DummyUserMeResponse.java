package com.sasa.user_service.adapter.output.dto.response;

public record DummyUserMeResponse(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String role
) {}
