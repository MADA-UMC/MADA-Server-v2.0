package com.mada.server.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!prod")
@OpenAPIDefinition(
    info = @Info(
        title = "MADA v2",
        version = "v1.0.0",
        description = "MADA v2 OpenAPI"
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "local Server")
    },
    security = {
        @SecurityRequirement(name = "BearerAuth")
    }
)
@SecurityScheme(
    name = "BearerAuth",
    type = SecuritySchemeType.HTTP,
    paramName = "Authorization",
    in = SecuritySchemeIn.HEADER,
    scheme = "Bearer",
    bearerFormat = "JWT",
    description = "Bearer 뒷 부분 입력"
)
public class OpenAPIConfig {

    @Bean
    public GroupedOpenApi apiV1() {
        return GroupedOpenApi.builder()
            .group("v1")
            .pathsToMatch("/api/v1/**")
            .build();
    }
}
