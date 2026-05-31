package com.condominios.sgc.application.dto;

import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IniciarPrestamoRequest(
    @NotNull TipoHabitante solicitante,
    @NotBlank String nombreSolicitante,
    @NotBlank String dniSolicitante,
    @NotNull Long apartamentoId,
    @NotNull Long carritoId
) {}