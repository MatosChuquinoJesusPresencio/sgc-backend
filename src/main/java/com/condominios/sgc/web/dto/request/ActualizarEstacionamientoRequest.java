package com.condominios.sgc.web.dto.request;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import jakarta.validation.constraints.NotNull;

public record ActualizarEstacionamientoRequest(
    @NotNull Integer numero,
    TipoVehiculo tipoVehiculo,
    Integer capacidadMaxima,
    Long idApartamento,
    boolean desasignarApartamento
) {}
