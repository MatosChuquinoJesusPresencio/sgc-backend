package com.condominios.sgc.application.dto;

import java.math.BigDecimal;

public record ActualizarApartamentoRequest(
        Integer numero,
        Boolean derechoEstacionamiento,
        BigDecimal metraje)
{}