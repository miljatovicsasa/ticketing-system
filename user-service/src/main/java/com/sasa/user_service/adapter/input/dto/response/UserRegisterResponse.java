package com.sasa.user_service.adapter.input.dto.response;

public record UserRegisterResponse(
        Long id,
        String email,
        String username,
        String firstName,
        String lastName
) {}
