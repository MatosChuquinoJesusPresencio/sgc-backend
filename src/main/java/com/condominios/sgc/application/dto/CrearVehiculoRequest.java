package com.condominios.sgc.application.dto;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearVehiculoRequest(
    @NotBlank String marca,
    @NotBlank String color,
    @NotBlank String modelo,
    @NotBlank String placa,
    @NotNull TipoVehiculo tipo,
    Long propietarioId,
    Long inquilinoId,
    Long estacionamientoId
) {}