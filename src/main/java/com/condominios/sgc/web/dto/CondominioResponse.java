package com.condominios.sgc.web.dto;

import com.condominios.sgc.domain.model.CondominioModel;
import java.time.LocalDateTime;

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
