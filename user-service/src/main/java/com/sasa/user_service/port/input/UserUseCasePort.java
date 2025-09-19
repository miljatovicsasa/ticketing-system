package com.sasa.user_service.port.input;

import com.sasa.user_service.adapter.output.dto.response.UserWithTokenResponse;
import com.sasa.user_service.domain.model.User;

public interface UserUseCasePort {
    User register(User user, String password);
    UserWithTokenResponse login(User user, String password);
    User getCurrentUser(String token);
}
