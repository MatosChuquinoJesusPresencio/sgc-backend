package com.condominios.sgc.infrastructure.web.dto;

import com.condominios.sgc.domain.model.PisoModel;

public record PisoResponse(Long id, Integer numero, Long torreId) {
    public static PisoResponse fromModel(PisoModel model) {
        return new PisoResponse(model.getId(), model.getNumero(), model.getTorreId());
    }
}
