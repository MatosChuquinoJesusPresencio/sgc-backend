package com.condominios.sgc.application.dto.result;

public record IniciarSesionResult(
    String accessToken,
    String refreshToken,
    Long idUsuario,
    String correo,
    String nombres,
    String apellidos,
    String rol
) {}
