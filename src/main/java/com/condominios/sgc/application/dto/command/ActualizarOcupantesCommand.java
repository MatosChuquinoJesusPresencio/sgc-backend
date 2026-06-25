package com.condominios.sgc.application.dto.command;

import java.util.List;

import com.condominios.sgc.domain.type.TipoDocumento;

public record ActualizarOcupantesCommand(
    List<InquilinoEntry> inquilinos
) {
    public record InquilinoEntry(
        String nombres,
        String apellidos,
        TipoDocumento tipoDocumento,
        String numeroDocumento
    ) {}
}
