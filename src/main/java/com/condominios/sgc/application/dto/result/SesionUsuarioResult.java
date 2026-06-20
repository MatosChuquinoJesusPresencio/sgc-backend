package com.condominios.sgc.application.dto.result;

public record SesionUsuarioResult(
    String tokenAcceso,
    String tokenRefresco,
    UsuarioActualResult usuarioActual
) {}
