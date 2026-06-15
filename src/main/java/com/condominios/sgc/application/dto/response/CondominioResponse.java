package com.condominios.sgc.application.dto.response;

import java.time.Instant;
import com.condominios.sgc.domain.model.CondominioModel;

public record CondominioResponse(
    Long id,
    String nombre,
    String nombrePais,
    String nombreCiudad,
    String direccion,
    Instant fechaCreacion
) {
    public static CondominioResponse desdeModelo(CondominioModel model, String nombrePais, String nombreCiudad) {
        return new CondominioResponse(
            model.getId(), model.getNombre(), nombrePais,
            nombreCiudad, model.getDireccion(), model.getFechaCreacion());
    }
}
