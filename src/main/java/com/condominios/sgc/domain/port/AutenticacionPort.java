package com.condominios.sgc.domain.port;

import java.util.Optional;

public interface AutenticacionPort {
    String hashContrasena(String contrasena);
    boolean verificarContrasena(String contrasena, String hash);
    Optional<Long> validarToken(String token);
    void invalidarToken(String token);
}
