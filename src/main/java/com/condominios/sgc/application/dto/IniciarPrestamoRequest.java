package com.condominios.sgc.application.dto;

import com.condominios.sgc.domain.auxiliar.TipoHabitante;

public record IniciarPrestamoRequest(
    TipoHabitante solicitante,
    String nombreSolicitante,
    String dniSolicitante,
    Long apartamentoId,
    Long carritoId
) {}
