package com.condominios.sgc.application.dto.response;

public record LoginResponse(
    String token,
    Long idUsuario,
    String rol,
    String nombres
) {}
