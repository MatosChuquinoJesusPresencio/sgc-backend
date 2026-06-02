package com.condominios.sgc.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearInquilinoRequest(
    @NotBlank String nombres,
    @NotBlank String apellidos,
    @NotBlank String dni,
    @NotNull Long apartamentoId
) {}