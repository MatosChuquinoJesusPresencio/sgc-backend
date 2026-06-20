package com.condominios.sgc.application.dto.result;

public record SesionUsuarioResult(
    UsuarioActualResult usuarioActual,
    String tokenAcceso,
    String tokenRefresco
) {}
