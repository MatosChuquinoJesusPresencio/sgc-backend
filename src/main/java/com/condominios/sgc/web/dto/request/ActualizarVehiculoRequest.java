package com.condominios.sgc.web.dto.request;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarVehiculoRequest(
    @NotBlank String marca,
    @NotBlank String color,
    @NotBlank String modelo,
    @NotBlank String placa,
    @NotNull TipoVehiculo tipo,
    Long idPropietario,
    boolean desasignarPropietario,
    Long idInquilino,
    boolean desasignarInquilino,
    Long idEstacionamiento,
    boolean desasignarEstacionamiento,
    @NotNull Long idCondominio
) {}
