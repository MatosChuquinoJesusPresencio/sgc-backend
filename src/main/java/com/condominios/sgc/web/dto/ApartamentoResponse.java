package com.condominios.sgc.web.dto;

import com.condominios.sgc.domain.model.ApartamentoModel;
import java.math.BigDecimal;

public record ApartamentoResponse(Long id,
                                  Integer numero,
                                  Boolean derechoEstacionamiento,
                                  BigDecimal metraje, Long pisoId,
                                  String propietarioId) {
    public static ApartamentoResponse fromModel(ApartamentoModel model) {
        return new ApartamentoResponse(model.getId(),
                model.getNumero(),
                model.getDerechoEstacionamiento(),
                model.getMetraje(), model.getPisoId(),
                model.getPropietarioId());
    }
}