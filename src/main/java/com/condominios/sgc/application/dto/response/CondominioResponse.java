package com.condominios.sgc.application.dto.response;

import java.time.Instant;
import com.condominios.sgc.domain.model.CondominioModel;

public record CondominioResponse(
    Long id,
    String nombre,
    Long idPais,
    Long idCiudad,
    String direccion,
    Instant fechaCreacion
) {
    public static CondominioResponse desdeModelo(CondominioModel model) {
        return new CondominioResponse(
            model.getId(), model.getNombre(), model.getIdPais(),
            model.getIdCiudad(), model.getDireccion(), model.getFechaCreacion());
    }
}
