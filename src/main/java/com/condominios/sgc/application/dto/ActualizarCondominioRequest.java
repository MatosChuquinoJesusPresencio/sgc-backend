package com.condominios.sgc.application.dto;

import jakarta.validation.constraints.NotBlank;

public record ActualizarCondominioRequest(
    @NotBlank String nombre,
    @NotBlank String pais,
    @NotBlank String ciudad,
    @NotBlank String direccion
) {}