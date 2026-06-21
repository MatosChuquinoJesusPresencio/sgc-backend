package com.condominios.sgc.infrastructure.adapter.out.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.condominios.sgc.application.port.out.service.HashServicePort;

@Component
public class HashServiceAdapter implements HashServicePort {

    private final PasswordEncoder passwordEncoder;

    public HashServiceAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean verificar(String contrasena, String hash) {
        return passwordEncoder.matches(contrasena, hash);
    }

    @Override
    public String hashear(String contrasena) {
        return passwordEncoder.encode(contrasena);
    }
}
