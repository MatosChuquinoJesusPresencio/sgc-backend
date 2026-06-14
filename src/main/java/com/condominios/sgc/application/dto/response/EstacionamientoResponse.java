package com.condominios.sgc.application.dto.response;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.model.EstacionamientoModel;

public record EstacionamientoResponse(
    Long id,
    Integer numero,
    TipoVehiculo tipoVehiculo,
    Integer capacidadMaxima,
    Integer cantidadActual,
    Boolean disponible,
    Long idApartamento,
    Long idCondominio
) {
    public static EstacionamientoResponse desdeModelo(EstacionamientoModel model) {
        return new EstacionamientoResponse(
            model.getId(), model.getNumero(), model.getTipoVehiculo(),
            model.getCapacidadMaxima(), model.getCantidadActual(),
            model.getDisponible(), model.getIdApartamento(), model.getIdCondominio());
    }
}
