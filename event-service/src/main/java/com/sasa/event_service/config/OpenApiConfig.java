package com.sasa.event_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Event Service API",
                version = "1.0",
                description = "API for managing events",
                contact = @Contact(name = "Saša", email = "miljatovic00@gmail.com")
        )
)
public class OpenApiConfig {
}
