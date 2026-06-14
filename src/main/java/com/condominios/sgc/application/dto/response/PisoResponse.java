package com.condominios.sgc.application.dto.response;

import com.condominios.sgc.domain.model.PisoModel;

public record PisoResponse(
    Long id,
    Integer numero,
    Long idTorre
) {
    public static PisoResponse desdeModelo(PisoModel model) {
        return new PisoResponse(model.getId(), model.getNumero(), model.getIdTorre());
    }
}