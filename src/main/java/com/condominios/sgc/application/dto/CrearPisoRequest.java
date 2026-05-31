package com.condominios.sgc.application.dto;

import jakarta.validation.constraints.NotNull;

public record CrearPisoRequest(
    @NotNull Integer numero,
    @NotNull Long torreId
) {}