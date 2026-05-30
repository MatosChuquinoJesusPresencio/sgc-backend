package com.condominios.sgc.application.dto;

import java.math.BigDecimal;

public record CrearApartamentoRequest(
    Integer numero,
    Boolean derechoEstacionamiento,
    BigDecimal metraje,
    Long pisoId
) {}
