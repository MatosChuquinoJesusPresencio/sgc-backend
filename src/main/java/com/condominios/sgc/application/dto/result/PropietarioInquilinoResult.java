package com.condominios.sgc.application.dto.result;

public record PropietarioInquilinoResult(
    Long id,
    String nombres,
    String apellidos,
    String tipoDocumento,
    String numeroDocumento
) {
}
