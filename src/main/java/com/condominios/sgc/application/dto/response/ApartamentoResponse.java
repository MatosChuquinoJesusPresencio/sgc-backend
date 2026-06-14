package com.condominios.sgc.application.dto.response;

import java.math.BigDecimal;

import com.condominios.sgc.domain.model.ApartamentoModel;

public record ApartamentoResponse(
    Long id,
    Integer numero,
    Boolean derechoEstacionamiento,
    BigDecimal metraje,
    Long idPropietario,
    Long idPiso
) {
    public static ApartamentoResponse desdeModelo(ApartamentoModel model) {
        return new ApartamentoResponse(
            model.getId(), model.getNumero(), model.getDerechoEstacionamiento(),
            model.getMetraje(), model.getIdPropietario(), model.getIdPiso());
    }
}
