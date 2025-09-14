package com.example.project.infraestructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product Management API")
                        .description("API para gestión de productos inspirada en MercadoLibre. " +
                                   "Implementa Clean Architecture y Domain-Driven Design (DDD) " +
                                   "con patrones de diseño como Builder y Value Objects.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Daniel Urrego Zapata")
                                .email("danielurregozapata@gmail.com")
                                .url("https://github.com/daniel1795/project")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desarrollo")
                ));
    }
}
