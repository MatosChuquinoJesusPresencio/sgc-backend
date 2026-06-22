package com.condominios.sgc.infrastructure.web.dto;

public record PropietarioInquilinoResponse(
    Long id,
    String nombres,
    String apellidos,
    String tipoDocumento,
    String numeroDocumento
) {
}
