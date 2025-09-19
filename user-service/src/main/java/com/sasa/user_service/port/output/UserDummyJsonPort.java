package com.sasa.user_service.port.output;

import com.sasa.user_service.adapter.output.dto.response.UserWithTokenResponse;
import com.sasa.user_service.domain.model.User;

public interface UserDummyJsonPort {

    User createUser(User user, String rawPassword);

    UserWithTokenResponse loginUser(User user, String rawPassword);

    User getCurrentUser(String token);
}
