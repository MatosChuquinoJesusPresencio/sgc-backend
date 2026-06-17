package com.condominios.sgc.application.dto.result;

public record InquilinoResumen(
    Long id,
    String nombres,
    String apellidos,
    String tipoDocumento,
    String numeroDocumento
) {}
