package com.condominios.sgc.infrastructure.web.dto;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.model.EstacionamientoModel;

public record EstacionamientoResponse(
    Long id,
    Integer numero,
    TipoVehiculo tipoVehiculo,
    Integer capacidadMaxima,
    Integer cantidadActual,
    Boolean disponible,
    Long apartamentoId,
    Long condominioId
) {
    public static EstacionamientoResponse fromModel(EstacionamientoModel model) {
        return new EstacionamientoResponse(
            model.getId(),
            model.getNumero(),
            model.getTipoVehiculo(),
            model.getCapacidadMaxima(),
            model.getCantidadActual(),
            model.isDisponible(),
            model.getApartamentoId(),
            model.getCondominioId()
        );
    }
}
