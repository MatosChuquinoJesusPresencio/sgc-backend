package com.condominios.sgc.application.dto.command;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;

public record CrearVehiculoCommand(
    String marca,
    String color,
    String modelo,
    String placa,
    TipoVehiculo tipo
) {}
