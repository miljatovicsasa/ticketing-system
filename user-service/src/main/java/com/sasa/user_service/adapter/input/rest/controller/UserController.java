package com.sasa.user_service.adapter.input.rest.controller;

import com.sasa.user_service.adapter.input.dto.request.UserLoginRequest;
import com.sasa.user_service.adapter.input.dto.request.UserRegisterRequest;
import com.sasa.user_service.adapter.input.dto.response.ApiResponseDto;
import com.sasa.user_service.adapter.input.dto.response.AuthenticatedUser;
import com.sasa.user_service.adapter.input.dto.response.UserGetMeResponse;
import com.sasa.user_service.adapter.input.dto.response.UserRegisterResponse;
import com.sasa.user_service.adapter.input.mapper.UserDtoMapper;
import com.sasa.user_service.adapter.output.dto.response.UserWithTokenResponse;
import com.sasa.user_service.domain.model.User;
import com.sasa.user_service.port.input.UserUseCasePort;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "Operations related to user management")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserUseCasePort userPort;

    public UserController(UserUseCasePort userPort) {
        this.userPort = userPort;
    }

    @Operation(summary = "Register a new user",
            description = "Registers a new user with email and password. No authentication required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid registration request")
    })
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<UserRegisterResponse>> registerUser(
            @RequestBody @Valid UserRegisterRequest request) {

        User userDomain = UserDtoMapper.registerRequestToDomain(request);
        User user = userPort.register(userDomain, request.password());
        UserRegisterResponse responseDto = UserDtoMapper.domainToRegisterResponse(user);

        return ResponseEntity.ok(new ApiResponseDto<>(200, "User registered successfully", responseDto));
    }

    @Operation(summary = "Login a user",
            description = "Authenticates user and returns a JWT token. No authentication required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<AuthenticatedUser>> loginUser(
            @RequestBody @Valid UserLoginRequest request) {

        User userDomain = UserDtoMapper.loginRequestToDomain(request);
        UserWithTokenResponse userWithToken = userPort.login(userDomain, request.password());
        AuthenticatedUser responseDto = UserDtoMapper.domainToAuthenticatedUser(userWithToken.user(), userWithToken.token());

        return ResponseEntity.ok(new ApiResponseDto<>(200, "User logged in successfully", responseDto));
    }

    @Operation(summary = "Get current user",
            description = "Fetches information about the currently authenticated user. Requires Authorization header with Bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Current user fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token")
    })

    @Hidden
    @GetMapping("/me")
    public ResponseEntity<ApiResponseDto<UserGetMeResponse>> getCurrentUser(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        User user = userPort.getCurrentUser(token);
        UserGetMeResponse responseDto = UserDtoMapper.domainToGetMeResponse(user);

        return ResponseEntity.ok(new ApiResponseDto<>(200, "Current user fetched successfully", responseDto));
    }
}
