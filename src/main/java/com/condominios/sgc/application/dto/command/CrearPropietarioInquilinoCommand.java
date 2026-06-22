package com.condominios.sgc.application.dto.command;

public record CrearPropietarioInquilinoCommand(
    String nombres,
    String apellidos,
    String tipoDocumento,
    String numeroDocumento
) {
}
