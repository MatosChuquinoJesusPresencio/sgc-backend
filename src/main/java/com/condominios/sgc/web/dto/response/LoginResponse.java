package com.condominios.sgc.web.dto.response;

public record LoginResponse(
    Long idUsuario,
    String rol,
    String nombres
) {}
