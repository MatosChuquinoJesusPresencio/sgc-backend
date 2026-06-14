package com.condominios.sgc.application.dto.response;

import com.condominios.sgc.domain.model.CiudadModel;

public record CiudadResponse(
    Long id,
    String nombre,
    Long idPais
) {
    public static CiudadResponse desdeModelo(CiudadModel model) {
        return new CiudadResponse(model.getId(), model.getNombre(), model.getIdPais());
    }
}
