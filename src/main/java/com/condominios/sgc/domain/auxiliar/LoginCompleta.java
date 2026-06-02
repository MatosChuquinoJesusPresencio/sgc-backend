package com.condominios.sgc.domain.auxiliar;

import com.condominios.sgc.domain.model.UsuarioModel;

public record LoginCompleta(
    String accessToken,
    String refreshToken,
    String tokenType,
    long expiresIn,
    long expiresAt,
    long refreshExpiresIn,
    UsuarioModel usuario
) {}
