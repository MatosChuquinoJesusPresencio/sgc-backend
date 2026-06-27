package com.condominios.sgc.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.condominios.sgc.application.port.out.service.CorreoServicePort;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public CorreoServicePort correoServicePort() {
        return new CorreoServicePort() {
            @Override
            public void enviarBienvenida(String destino, String nombreCompleto, String contrasena) {
            }

            @Override
            public void enviarRestablecimiento(String destino, String nombreCompleto, String token) {
            }

            @Override
            public void enviarVerificacion(String destino, String nombreCompleto, String token) {
            }
        };
    }
}
