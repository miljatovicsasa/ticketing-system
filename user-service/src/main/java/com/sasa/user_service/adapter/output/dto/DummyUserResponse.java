package com.sasa.user_service.adapter.output.dto;

public record DummyUserResponse(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String role,
        String token
) {}
