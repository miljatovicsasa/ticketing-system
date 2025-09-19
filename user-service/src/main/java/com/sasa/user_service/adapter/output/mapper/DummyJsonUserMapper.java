package com.sasa.user_service.adapter.output.mapper;

import com.sasa.user_service.adapter.output.dto.request.DummyUserLoginRequest;
import com.sasa.user_service.adapter.output.dto.request.DummyUserRegisterRequest;
import com.sasa.user_service.adapter.output.dto.response.DummyUserLoginResponse;
import com.sasa.user_service.adapter.output.dto.response.DummyUserRegisterResponse;
import com.sasa.user_service.adapter.output.dto.response.UserWithTokenResponse;
import com.sasa.user_service.domain.model.User;
import com.sasa.user_service.domain.model.UserRoles;

public class DummyJsonUserMapper {

    public static DummyUserLoginRequest domainToLoginRequest(User user, String password) {
        return new DummyUserLoginRequest(user.getUsername(), password);
    }

    public static DummyUserRegisterRequest domainToRegisterRequest(User user, String password) {
        return new DummyUserRegisterRequest(
                user.getEmail(),
                user.getUsername(),
                password,
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name()
        );
    }

    public static User loginResponseToDomain(DummyUserLoginResponse response) {
        return new User(
                response.id(),
                response.username(),
                response.email(),
                response.firstName(),
                response.lastName(),
                UserRoles.fromString(response.role())
        );
    }

    public static UserWithTokenResponse loginResponseToDomainWithToken(User user, String token) {
        return new UserWithTokenResponse(user, token);
    }

    public static User registerResponseToDomain(DummyUserRegisterResponse response) {
        return new User(
                response.id(),
                response.username(),
                response.email(),
                response.firstName(),
                response.lastName(),
                UserRoles.fromString(response.role())
        );
    }
}
