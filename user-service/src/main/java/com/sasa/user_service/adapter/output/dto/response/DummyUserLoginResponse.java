package com.sasa.user_service.adapter.output.dto.response;

public record DummyUserLoginResponse(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String role,
        String accessToken
) {}
