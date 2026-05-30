package com.condominios.sgc.domain.dto;

public record SesionUsuario(
    String accessToken,
    String refreshToken,
    String tokenType,
    long expiresIn,
    long expiresAt,
    UsuarioAutenticado usuario
) {}
