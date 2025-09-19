package com.sasa.user_service.adapter.output.dto.request;

public record DummyUserRegisterRequest(
        String email,
        String username,
        String password,
        String firstName,
        String lastName,
        String role
) {}
