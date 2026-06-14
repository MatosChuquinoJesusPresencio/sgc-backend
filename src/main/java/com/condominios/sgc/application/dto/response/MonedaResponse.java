package com.condominios.sgc.application.dto.response;

import com.condominios.sgc.domain.model.MonedaModel;

public record MonedaResponse(
    Long id,
    String nombre,
    String codigo,
    String simbolo
) {
    public static MonedaResponse desdeModelo(MonedaModel model) {
        return new MonedaResponse(model.getId(), model.getNombre(), model.getCodigo(), model.getSimbolo());
    }
}
