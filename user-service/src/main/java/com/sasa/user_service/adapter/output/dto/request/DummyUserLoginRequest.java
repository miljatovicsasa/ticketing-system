package com.sasa.user_service.adapter.output.dto.request;

public record DummyUserLoginRequest(
        String username,
        String password
) {}