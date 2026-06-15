package com.condominios.sgc.web.dto;

public record ErrorResponse(
    int estado,
    String error,
    String mensaje
) {}
