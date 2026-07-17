package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.util.List;

public record SecurityApartamentoResponse(
    Long id,
    Integer numero,
    String torreNombre,
    Integer pisoNumero,
    Long idPropietario,
    String nombrePropietario,
    List<InquilinoInfo> inquilinos
) {
    public record InquilinoInfo(
        Long id, String nombres, String apellidos, String numeroDocumento
    ) {}
}
