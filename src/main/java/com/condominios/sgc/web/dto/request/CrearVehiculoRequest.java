package com.condominios.sgc.web.dto.request;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearVehiculoRequest(
    @NotBlank String marca,
    @NotBlank String color,
    @NotBlank String modelo,
    @NotBlank String placa,
    @NotNull TipoVehiculo tipo,
    @NotNull Long idCondominio
) {}
