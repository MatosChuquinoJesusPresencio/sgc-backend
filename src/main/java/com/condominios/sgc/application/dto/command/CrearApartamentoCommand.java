package com.condominios.sgc.application.dto.command;

import java.math.BigDecimal;

public record CrearApartamentoCommand(
    Integer numero,
    BigDecimal metraje,
    Long idPiso
) {}
