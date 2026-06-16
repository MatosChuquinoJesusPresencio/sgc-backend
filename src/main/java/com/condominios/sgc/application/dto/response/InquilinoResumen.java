package com.condominios.sgc.application.dto.response;

public record InquilinoResumen(
    Long id,
    String nombres,
    String apellidos,
    String tipoDocumento,
    String numeroDocumento
) {}
