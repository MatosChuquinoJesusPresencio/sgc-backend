package com.condominios.sgc.application.dto.response;

public record IniciarSesionResponse(
    String token,
    String refreshToken,
    Long idUsuario,
    String rol,
    String nombres
) {}