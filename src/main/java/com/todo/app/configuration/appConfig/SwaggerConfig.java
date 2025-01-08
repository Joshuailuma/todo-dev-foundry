package com.todo.app.configuration.appConfig;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(info = @Info(
        contact = @Contact(
                name = "Todo",
                url = "https://www.buildwithseamless.co"),
        title = "Todo Devfoundry API",
        version = "1.0",
        description = "This is the API documentation for the Dev foundry API",
        license = @License(
                name = "Apache 2.0",
                url = "http://www.apache.org/licenses/LICENSE-2.0.html"
        ),
        termsOfService = "http://swagger.io/terms/"),
        servers = {
        @Server(url = "http://localhost:8086",
                description = "Local Server"),

        @Server(url = "http://3.89.102.55",
                description = "Production Server")
        },
        security = {
                @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth",
                        scopes = {"read", "write"})})
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}