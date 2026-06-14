package com.condominios.sgc.application.dto.command;

import java.math.BigDecimal;

public record ActualizarApartamentoCommand(
    Integer numero,
    BigDecimal metraje,
    boolean desasignarPropietario,
    Long idPropietario
) {}
