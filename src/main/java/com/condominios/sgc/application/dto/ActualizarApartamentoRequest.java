package com.condominios.sgc.application.dto;

import java.math.BigDecimal;

public record ActualizarApartamentoRequest(
    Integer numero,
    BigDecimal metraje,
    Boolean derechoEstacionamiento
) {}
