package com.condominios.sgc.web.dto;

import com.condominios.sgc.domain.model.ConfiguracionModel;
import java.math.BigDecimal;

public record ConfiguracionResponse(
    Long id,
    Integer maxAutos,
    Integer maxMotos,
    BigDecimal penalizacionPorMin,
    Integer maxTiempoPrestamoMin,
    Integer maxEstacionamientosPorApartamento,
    Integer maxCarritosPorApartamento,
    Integer maxVehiculosPorPropietario,
    Integer maxInquilinosPorApartamento,
    Long condominioId
) {
    public static ConfiguracionResponse fromModel(ConfiguracionModel model) {
        return new ConfiguracionResponse(
            model.getId(),
            model.getMaxAutos(),
            model.getMaxMotos(),
            model.getPenalizacionPorMin(),
            model.getMaxTiempoPrestamoMin(),
            model.getMaxEstacionamientosPorApartamento(),
            model.getMaxCarritosPorApartamento(),
            model.getMaxVehiculosPorPropietario(),
            model.getMaxInquilinosPorApartamento(),
            model.getCondominioId()
        );
    }
}
