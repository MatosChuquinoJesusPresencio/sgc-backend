package com.condominios.sgc.domain.auxiliar;

public record SesionUsuario(
    String accessToken,
    String tokenType,
    long expiresIn,
    long expiresAt,
    String refreshToken,
    UsuarioAutenticado usuario
) {}
