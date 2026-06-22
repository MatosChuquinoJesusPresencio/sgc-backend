package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

public record PropietarioInquilinoResponse(
    Long id,
    String nombres,
    String apellidos,
    String tipoDocumento,
    String numeroDocumento
) {
}
