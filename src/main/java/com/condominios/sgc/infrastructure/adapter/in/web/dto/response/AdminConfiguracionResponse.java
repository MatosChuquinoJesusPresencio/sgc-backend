package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.math.BigDecimal;

public record AdminConfiguracionResponse(
    Integer maxAutos,
    Integer maxMotos,
    BigDecimal penalizacionPorMin,
    Integer maxTiempoPrestamoMin,
    Integer maxEstacionamientos,
    Integer maxCarritos,
    Integer maxVehiculos,
    Integer maxInquilinos
) {
}
