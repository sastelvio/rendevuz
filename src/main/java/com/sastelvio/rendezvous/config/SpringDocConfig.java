package com.sastelvio.rendezvous.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Rendezvous API")
                                .version("1")
                                .description("This is a project in Java Spring Boot to practice")
                                .license(
                                        new License()
                                                .name("Apache 2.0")
                                                .url("https://springdoc.org")
                                )
                );
    }
}
