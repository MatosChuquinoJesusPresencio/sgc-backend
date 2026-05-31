package com.condominios.sgc.application.dto;

import jakarta.validation.constraints.NotNull;

public record ActualizarEstacionamientoRequest(
    @NotNull Integer numero
) {}