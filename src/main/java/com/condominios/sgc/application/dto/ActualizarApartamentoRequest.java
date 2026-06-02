package com.condominios.sgc.application.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ActualizarApartamentoRequest(
    @NotNull Integer numero,
    @NotNull BigDecimal metraje,
    @NotNull Boolean derechoEstacionamiento
) {}