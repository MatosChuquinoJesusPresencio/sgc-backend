package com.condominios.sgc.application.dto.command;

import com.condominios.sgc.domain.auxiliar.TipoDocumento;

public record CrearInquilinoCommand(
    String nombres,
    String apellidos,
    TipoDocumento tipoDocumento,
    String numeroDocumento,
    Long idApartamento,
    Long idCondominio
) {}
