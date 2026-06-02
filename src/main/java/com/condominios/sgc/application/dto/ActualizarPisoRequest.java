package com.condominios.sgc.application.dto;

import jakarta.validation.constraints.NotNull;

public record ActualizarPisoRequest(
    @NotNull Integer numero
) {}