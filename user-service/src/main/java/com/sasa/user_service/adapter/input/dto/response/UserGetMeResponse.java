package com.sasa.user_service.adapter.input.dto.response;

public record UserGetMeResponse(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String role
) {}
