package com.condominios.sgc.web.dto;

import com.condominios.sgc.domain.auxiliar.SesionUsuario;

public record AuthResponse(
    String accessToken,
    String refreshToken,
    String tokenType,
    long expiresIn,
    long expiresAt,
    UsuarioResponse usuario
) {
    public static AuthResponse fromSesion(SesionUsuario sesion, UsuarioResponse usuario) {
        return new AuthResponse(
            sesion.accessToken(),
            sesion.refreshToken(),
            sesion.tokenType(),
            sesion.expiresIn(),
            sesion.expiresAt(),
            usuario
        );
    }
}
