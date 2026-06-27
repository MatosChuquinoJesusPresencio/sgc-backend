package com.condominios.sgc.application.dto.result;

import java.math.BigDecimal;

public record AdminConfiguracionResult(
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
