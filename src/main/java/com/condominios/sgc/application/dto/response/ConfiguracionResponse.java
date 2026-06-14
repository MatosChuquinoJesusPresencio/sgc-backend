package com.condominios.sgc.application.dto.response;

import java.math.BigDecimal;
import com.condominios.sgc.domain.model.ConfiguracionModel;

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
    Long idCondominio
) {
    public static ConfiguracionResponse desdeModelo(ConfiguracionModel model) {
        return new ConfiguracionResponse(
            model.getId(), model.getMaxAutos(), model.getMaxMotos(),
            model.getPenalizacionPorMin(), model.getMaxTiempoPrestamoMin(),
            model.getMaxEstacionamientosPorApartamento(), model.getMaxCarritosPorApartamento(),
            model.getMaxVehiculosPorPropietario(), model.getMaxInquilinosPorApartamento(),
            model.getIdCondominio());
    }
}
