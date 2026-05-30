package com.condominios.sgc.application.dto;

import com.condominios.sgc.domain.dto.UsuarioAutenticado;

public record SesionCompleta(
    String accessToken,
    String refreshToken,
    String tokenType,
    long expiresIn,
    long expiresAt,
    UsuarioAutenticado usuario
) {}
