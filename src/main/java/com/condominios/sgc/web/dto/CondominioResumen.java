package com.condominios.sgc.web.dto;

import java.time.LocalDateTime;

import com.condominios.sgc.domain.model.CondominioModel;

public record CondominioResumen(
    Long id,
    String nombre,
    String ciudad,
    String pais,
    LocalDateTime fechaCreacion
) {
    public static CondominioResumen fromModel(CondominioModel model) {
        return new CondominioResumen(
            model.getId(),
            model.getNombre(),
            model.getCiudad(),
            model.getPais(),
            model.getFechaCreacion()
        );
    }
}
