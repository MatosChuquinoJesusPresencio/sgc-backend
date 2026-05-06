package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.infrastructure.persistence.entity.ConfiguracionEntity;

public final class ConfiguracionMapper {

    private ConfiguracionMapper() {}

    public static ConfiguracionEntity toEntity(ConfiguracionModel model) {
        if (model == null) return null;
        ConfiguracionEntity e = new ConfiguracionEntity();
        e.setId(model.getId());
        e.setMaxAutos(model.getMaxAutos());
        e.setMaxMotos(model.getMaxMotos());
        e.setPenalizacionPorMin(model.getPenalizacionPorMin());
        e.setMaxTiempoPrestamoMin(model.getMaxTiempoPrestamoMin());
        e.setMaxEstacionamientosPorApartamento(model.getMaxEstacionamientosPorApartamento());
        e.setMaxCarritosPorApartamento(model.getMaxCarritosPorApartamento());
        e.setMaxVehiculosPorPropietario(model.getMaxVehiculosPorPropietario());
        e.setMaxInquilinosPorApartamento(model.getMaxInquilinosPorApartamento());
        return e;
    }

    public static ConfiguracionModel toModel(ConfiguracionEntity e) {
        if (e == null) return null;
        return new ConfiguracionModel(
                e.getId(), e.getMaxAutos(), e.getMaxMotos(), e.getPenalizacionPorMin(),
                e.getMaxTiempoPrestamoMin(), e.getMaxEstacionamientosPorApartamento(),
                e.getMaxCarritosPorApartamento(), e.getMaxVehiculosPorPropietario(),
                e.getMaxInquilinosPorApartamento());
    }
}
