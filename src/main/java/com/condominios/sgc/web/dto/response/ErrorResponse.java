package com.condominios.sgc.web.dto.response;

public record ErrorResponse(
    int estado,
    String error,
    String mensaje
) {}
