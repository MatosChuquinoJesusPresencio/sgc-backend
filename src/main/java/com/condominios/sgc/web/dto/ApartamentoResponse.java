package com.condominios.sgc.web.dto;

import com.condominios.sgc.domain.model.ApartamentoModel;

public record ApartamentoResponse(Long id, Integer numero, Boolean derechoEstacionamiento, Double metraje, Long pisoId, String propietarioId) {
    public static ApartamentoResponse fromModel(ApartamentoModel model) {
        return new ApartamentoResponse(
                model.getId(),
                model.getNumero(),
                model.getDerechoEstacionamiento(),
                model.getMetraje(),
                model.getPisoId(),
                model.getPropietarioId());
    }
}