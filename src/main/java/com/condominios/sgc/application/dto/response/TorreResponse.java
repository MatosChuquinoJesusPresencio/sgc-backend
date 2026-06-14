package com.condominios.sgc.application.dto.response;

import com.condominios.sgc.domain.model.TorreModel;

public record TorreResponse(
    Long id,
    String nombre,
    Long idCondominio
) {
    public static TorreResponse desdeModelo(TorreModel model) {
        return new TorreResponse(model.getId(), model.getNombre(), model.getIdCondominio());
    }
}
