package com.sasa.user_service.adapter.output;

import com.sasa.user_service.adapter.output.dto.request.DummyUserLoginRequest;
import com.sasa.user_service.adapter.output.dto.request.DummyUserRegisterRequest;
import com.sasa.user_service.adapter.output.dto.response.DummyUserLoginResponse;
import com.sasa.user_service.adapter.output.dto.response.DummyUserMeResponse;
import com.sasa.user_service.adapter.output.dto.response.DummyUserRegisterResponse;
import com.sasa.user_service.adapter.output.dto.response.UserWithTokenResponse;
import com.sasa.user_service.adapter.output.mapper.DummyJsonUserMapper;
import com.sasa.user_service.domain.model.User;
import com.sasa.user_service.domain.model.UserRoles;
import com.sasa.user_service.port.output.UserDummyJsonPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
public class DummyJsonAuthAdapter implements UserDummyJsonPort {

    private final WebClient webClient;
    private final String registerUserEndpoint;
    private final String loginUserEndpoint;
    private final String getCurrentUserEndpoint;

    public DummyJsonAuthAdapter(
            WebClient.Builder builder,
            @Value("${dummy-json.base-url}") String dummyJsonBaseUrl,
            @Value("${dummy-json.endpoint.register-user}") String registerUserEndpoint,
            @Value("${dummy-json.endpoint.login-user}") String loginUserEndpoint,
            @Value("${dummy-json.endpoint.get-current-user}") String getCurrentUserEndpoint
    ) {
        this.webClient = builder.baseUrl(dummyJsonBaseUrl).build();
        this.registerUserEndpoint = registerUserEndpoint;
        this.loginUserEndpoint = loginUserEndpoint;
        this.getCurrentUserEndpoint = getCurrentUserEndpoint;
    }

    @Override
    public User createUser(User user, String rawPassword) {
        DummyUserRegisterRequest request = DummyJsonUserMapper.domainToRegisterRequest(user, rawPassword);

        DummyUserRegisterResponse response = Optional.ofNullable(
                webClient.post()
                        .uri(registerUserEndpoint)
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(DummyUserRegisterResponse.class)
                        .block()
        ).orElseThrow(() -> new IllegalStateException("Register user response is null"));

        return DummyJsonUserMapper.registerResponseToDomain(response);
    }

    @Override
    public UserWithTokenResponse loginUser(User user, String rawPassword) {
        DummyUserLoginRequest request = DummyJsonUserMapper.domainToLoginRequest(user, rawPassword);

        DummyUserLoginResponse response = Optional.ofNullable(
                webClient.post()
                        .uri(loginUserEndpoint)
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(DummyUserLoginResponse.class)
                        .block()
        ).orElseThrow(() -> new IllegalStateException("Login user response is null"));

        User domain = DummyJsonUserMapper.loginResponseToDomain(response);
        return DummyJsonUserMapper.loginResponseToDomainWithToken(domain, response.accessToken());
    }


    @Override
    public User getCurrentUser(String token) {
        DummyUserMeResponse response = webClient.get()
                .uri(getCurrentUserEndpoint)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(DummyUserMeResponse.class)
                .block();

        return new User(
                response.id(),
                response.username(),
                response.email(),
                response.firstName(),
                response.lastName(),
                UserRoles.fromString(response.role().toUpperCase())
        );
    }
}
