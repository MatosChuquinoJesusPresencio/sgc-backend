package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

public record AdminInquilinoResponse(
    Long id,
    String nombres,
    String apellidos,
    String tipoDocumento,
    String numeroDocumento
) {
}
