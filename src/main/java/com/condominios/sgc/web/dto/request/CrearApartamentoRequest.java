package com.condominios.sgc.web.dto.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CrearApartamentoRequest(
    @NotNull Integer numero,
    BigDecimal metraje,
    @NotNull Long idPiso
) {}
