package com.condominios.sgc.application.dto.result;

public record AdminInquilinoResult(
    Long id,
    String nombres,
    String apellidos,
    String tipoDocumento,
    String numeroDocumento
) {
}
