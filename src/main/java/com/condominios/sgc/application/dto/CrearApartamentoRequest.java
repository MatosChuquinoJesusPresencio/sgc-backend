package com.condominios.sgc.application.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CrearApartamentoRequest(
    @NotNull Integer numero,
    @NotNull Boolean derechoEstacionamiento,
    @NotNull BigDecimal metraje,
    @NotNull Long pisoId
) {}