package com.sasa.user_service.adapter.output.dto.response;

import com.sasa.user_service.domain.model.User;

public record UserWithTokenResponse(
        User user,
        String token
) {}