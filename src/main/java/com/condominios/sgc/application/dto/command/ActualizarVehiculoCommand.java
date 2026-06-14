package com.condominios.sgc.application.dto.command;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;

public record ActualizarVehiculoCommand(
    String marca,
    String color,
    String modelo,
    String placa,
    TipoVehiculo tipo,
    Long idPropietario,
    boolean desasignarPropietario,
    Long idInquilino,
    boolean desasignarInquilino,
    Long idEstacionamiento,
    boolean desasignarEstacionamiento,
    Long idCondominio
) {}
