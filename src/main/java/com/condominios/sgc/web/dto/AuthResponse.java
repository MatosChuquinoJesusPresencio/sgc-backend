package com.condominios.sgc.web.dto;

import com.condominios.sgc.domain.auxiliar.SesionUsuario;

public record AuthResponse(
    String accessToken,
    String tokenType,
    long expiresIn,
    long expiresAt,
    String refreshToken,
    UsuarioResponse user
) {
    public static AuthResponse fromSesion(SesionUsuario sesion, UsuarioResponse user) {
        return new AuthResponse(
            sesion.accessToken(),
            sesion.tokenType(),
            sesion.expiresIn(),
            sesion.expiresAt(),
            sesion.refreshToken(),
            user
        );
    }
}
