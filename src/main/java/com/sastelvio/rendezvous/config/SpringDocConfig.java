package com.sastelvio.rendezvous.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@ComponentScan(basePackages = "com.sastelvio.rendezvous.api.v1.controller")
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("Bearer Authentication")
                )
                .components(
                        new Components().addSecuritySchemes(
                                "Bearer Authentication", createAPIKeyScheme()
                        )
                )
                .info(
                        new Info()
                                .title("Rendezvous API")
                                .version("v1.1")
                                .contact(
                                        new Contact()
                                                .name("Sastelvio MANUEL")
                                                .email("sastelvios@gmail.com")
                                                .url("https://github.com/sastelvio")
                                )
                                .description("This is a project in Java Spring Boot to practice")
                                .license(
                                        new License()
                                                .name("SpringDoc OpenAPI")
                                                .url("https://springdoc.org")
                                )
                )
                .tags(
                        Arrays.asList(
                                new Tag().name("Patient").description("Patient APIs"),
                                new Tag().name("Appointment").description("Appointment APIs")
                        )
                );
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}
