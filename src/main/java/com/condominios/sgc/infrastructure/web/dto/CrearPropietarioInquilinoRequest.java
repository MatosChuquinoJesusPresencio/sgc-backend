package com.condominios.sgc.infrastructure.web.dto;

public record CrearPropietarioInquilinoRequest(
    String nombres,
    String apellidos,
    String tipoDocumento,
    String numeroDocumento
) {
}
