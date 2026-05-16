package com.condominios.sgc.web.dto;

import java.time.LocalDateTime;

import com.condominios.sgc.domain.model.CondominioModel;

public record CondominioResponse(
    Long id,
    String nombre,
    String pais,
    String ciudad,
    String direccion,
    LocalDateTime fechaCreacion
) {
    public static CondominioResponse fromModel(CondominioModel model) {
        return new CondominioResponse(
            model.getId(),
            model.getNombre(),
            model.getPais(),
            model.getCiudad(),
            model.getDireccion(),
            model.getFechaCreacion()
        );
    }
}

