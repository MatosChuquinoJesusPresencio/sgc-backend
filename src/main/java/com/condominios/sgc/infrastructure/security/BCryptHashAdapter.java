package com.condominios.sgc.infrastructure.security;

import com.condominios.sgc.application.port.out.service.HashServicePort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptHashAdapter implements HashServicePort {

    private final BCryptPasswordEncoder encoder;

    public BCryptHashAdapter(BCryptPasswordEncoder encoder) {
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public String hashear(String contrasena) {
        return encoder.encode(contrasena);
    }

    @Override
    public boolean verificar(String contrasena, String hash) {
        return encoder.matches(contrasena, hash);
    }
}
