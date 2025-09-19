package com.sasa.ticket_service.adapter.input.security;

import com.sasa.ticket_service.adapter.input.dto.internal.AuthenticatedUserIncoming;
import com.sasa.ticket_service.adapter.input.security.principal.AuthPrincipal;
import com.sasa.ticket_service.adapter.input.dto.internal.InnerApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final WebClient.Builder webClientBuilder;

    @Value("${user-service.base-url}")
    private String userServiceBaseUrl;

    @Value("${event-service.endpoint.get-current-user}")
    private String getCurrentUserEndpoint;

    @Value("${event-service.endpoint.token-param}")
    private String getTokenParamName;

    public JwtAuthenticationFilter(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            AuthPrincipal principal = fetchPrincipalFromUserService(token);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    public AuthPrincipal fetchPrincipalFromUserService(String token) {
        AuthenticatedUserIncoming dto = Optional.ofNullable(
                        webClientBuilder.build()
                                .get()
                                .uri(userServiceBaseUrl + getCurrentUserEndpoint)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .retrieve()
                                .bodyToMono(new ParameterizedTypeReference<InnerApiResponse<AuthenticatedUserIncoming>>() {})
                                .block()
                ).map(InnerApiResponse::data)
                .orElseThrow(() -> new IllegalStateException("Failed to fetch user from user service"));

        return new AuthPrincipal(dto, token);
    }
}
