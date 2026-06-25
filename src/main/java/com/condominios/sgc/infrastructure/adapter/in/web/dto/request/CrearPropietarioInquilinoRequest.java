package com.condominios.sgc.infrastructure.adapter.in.web.dto.request;

public record CrearPropietarioInquilinoRequest(
    String nombres,
    String apellidos,
    String tipoDocumento,
    String numeroDocumento
) {
}
