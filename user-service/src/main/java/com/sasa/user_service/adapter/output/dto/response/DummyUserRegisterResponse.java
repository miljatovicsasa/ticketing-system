package com.sasa.user_service.adapter.output.dto.response;

public record DummyUserRegisterResponse(
        Long id,
        String email,
        String username,
        String firstName,
        String lastName,
        String role
) {}
