package com.condominios.sgc.application.dto;

import jakarta.validation.constraints.NotNull;

public record CrearEstacionamientoRequest(
    @NotNull Integer numero,
    @NotNull Integer capacidadMaxima,
    @NotNull Integer cantidadActual,
    @NotNull Boolean disponible,
    @NotNull Long condominioId
) {}