package com.condominios.sgc.application.dto;

import jakarta.validation.constraints.NotBlank;

public record ActualizarInquilinoRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos
) {}