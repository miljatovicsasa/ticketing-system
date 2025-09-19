package com.sasa.user_service.domain.service;

import com.sasa.user_service.adapter.output.dto.response.UserWithTokenResponse;
import com.sasa.user_service.domain.model.User;
import com.sasa.user_service.domain.model.UserRoles;
import com.sasa.user_service.port.input.UserUseCasePort;
import com.sasa.user_service.port.output.UserDummyJsonPort;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserUseCasePort {

    private final UserDummyJsonPort userRepositoryPort;

    public UserService(UserDummyJsonPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public User register(User user, String password) {
        user.setRole(UserRoles.user);
        return userRepositoryPort.createUser(user, password);
    }

    @Override
    public UserWithTokenResponse login(User user, String password) {
        return userRepositoryPort.loginUser(user, password);
    }

    @Override
    public User getCurrentUser(String token) {
        return userRepositoryPort.getCurrentUser(token);
    }
}
