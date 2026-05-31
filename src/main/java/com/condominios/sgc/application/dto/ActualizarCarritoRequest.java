package com.condominios.sgc.application.dto;

import jakarta.validation.constraints.NotBlank;

public record ActualizarCarritoRequest(
    @NotBlank String codigo
) {}