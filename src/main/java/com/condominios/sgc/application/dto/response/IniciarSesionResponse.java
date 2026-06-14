package com.condominios.sgc.application.dto.response;

public record IniciarSesionResponse(
    String token,
    Long idUsuario,
    String rol,
    String nombres
) {}