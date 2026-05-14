package com.redNorte.ms_listaEspera.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuracion de OpenAPI/Swagger para ms-listaEspera.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Lista Espera - RedNorte")
                        .version("1.0.0")
                        .description("API para gestion de lista de espera de citas medicas")
                        .contact(new Contact()
                                .name("Equipo RedNorte")
                                .email("contacto@rednorte.cl")))
                .servers(List.of(
                        new Server().url("http://localhost:8082").description("Servidor local")
                ));
    }
}