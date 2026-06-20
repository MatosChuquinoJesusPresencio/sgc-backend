package com.condominios.sgc.infrastructure.web.dto;

import com.condominios.sgc.domain.model.InquilinoModel;

public record InquilinoResponse(
    Long id,
    String nombres,
    String apellidos,
    String dni,
    Long apartamentoId
) {
    public static InquilinoResponse fromModel(InquilinoModel model) {
        return new InquilinoResponse(
            model.getId(),
            model.getNombres(),
            model.getApellidos(),
            model.getDni(),
            model.getApartamentoId()
        );
    }
}
