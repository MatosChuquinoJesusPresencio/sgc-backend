package com.condominios.sgc.web.dto.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ActualizarApartamentoRequest(
    @NotNull Integer numero,
    BigDecimal metraje,
    boolean desasignarPropietario,
    Long idPropietario
) {}
