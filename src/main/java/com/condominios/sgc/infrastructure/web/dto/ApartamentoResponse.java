package com.condominios.sgc.infrastructure.web.dto;

import com.condominios.sgc.domain.model.ApartamentoModel;
import java.math.BigDecimal;

public record ApartamentoResponse(
    Long id,
    Integer numero,
    Boolean derechoEstacionamiento,
    BigDecimal metraje,
    Long propietarioId,
    Long pisoId
) {
    public static ApartamentoResponse fromModel(ApartamentoModel model) {
        return new ApartamentoResponse(
            model.getId(),
            model.getNumero(),
            model.getDerechoEstacionamiento(),
            model.getMetraje(),
            model.getPropietarioId(),
            model.getPisoId()
        );
    }
}
