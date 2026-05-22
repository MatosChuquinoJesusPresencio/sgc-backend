package com.condominios.sgc.web.dto;

import com.condominios.sgc.domain.model.TorreModel;

public record TorreResponse(Long id, String nombre, Long condominioId) {
    public static TorreResponse fromModel(TorreModel model) {
        return new TorreResponse(model.getId(), model.getNombre(), model.getCondominioId());
    }
}