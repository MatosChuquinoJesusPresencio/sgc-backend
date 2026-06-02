package com.condominios.sgc.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearTorreRequest(
    @NotBlank String nombre,
    @NotNull Long condominioId
) {}