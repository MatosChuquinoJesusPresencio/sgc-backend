package com.condominios.sgc.application.dto.command;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;

public record ActualizarEstacionamientoCommand(
    Integer numero,
    TipoVehiculo tipoVehiculo,
    Integer capacidadMaxima,
    Long idApartamento,
    boolean desasignarApartamento
) {}
