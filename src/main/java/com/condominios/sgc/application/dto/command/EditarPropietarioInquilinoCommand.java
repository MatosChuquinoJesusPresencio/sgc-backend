package com.condominios.sgc.application.dto.command;

public record EditarPropietarioInquilinoCommand(
    String nombres,
    String apellidos,
    String tipoDocumento,
    String numeroDocumento
) {
}
