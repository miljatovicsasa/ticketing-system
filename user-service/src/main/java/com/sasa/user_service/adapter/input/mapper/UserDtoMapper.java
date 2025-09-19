package com.sasa.user_service.adapter.input.mapper;

import com.sasa.user_service.adapter.input.dto.request.UserLoginRequest;
import com.sasa.user_service.adapter.input.dto.request.UserRegisterRequest;
import com.sasa.user_service.adapter.input.dto.response.AuthenticatedUser;
import com.sasa.user_service.adapter.input.dto.response.UserGetMeResponse;
import com.sasa.user_service.adapter.input.dto.response.UserRegisterResponse;
import com.sasa.user_service.domain.model.User;
import com.sasa.user_service.domain.model.UserRoles;

public class UserDtoMapper {

    public static User loginRequestToDomain(UserLoginRequest dto) {
        if (dto == null) throw new IllegalArgumentException("UserLoginRequest must not be null");
        return new User(
                null,
                dto.username(),
                null,
                null,
                null,
                null
        );
    }

    public static User registerRequestToDomain(UserRegisterRequest dto) {
        if (dto == null) throw new IllegalArgumentException("UserRegisterRequest must not be null");
        return new User(
                null,
                dto.userName(),
                dto.email(),
                dto.firstName(),
                dto.lastName(),
                null
        );
    }

    public static UserRegisterResponse domainToRegisterResponse(User user) {
        if (user == null) throw new IllegalArgumentException("User must not be null");
        return new UserRegisterResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public static UserGetMeResponse domainToGetMeResponse(User user) {
        if (user == null) throw new IllegalArgumentException("User must not be null");
        return new UserGetMeResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole() != null ? user.getRole().name() : null
        );
    }

    public static AuthenticatedUser domainToAuthenticatedUser(User user, String token) {
        if (user == null) throw new IllegalArgumentException("User must not be null");
        return new AuthenticatedUser(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole() != null ? user.getRole().name() : null,
                token
        );
    }
}
