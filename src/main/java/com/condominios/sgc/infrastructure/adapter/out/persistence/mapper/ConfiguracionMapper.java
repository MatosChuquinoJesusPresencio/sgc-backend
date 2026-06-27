package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.ConfiguracionEntity;

public final class ConfiguracionMapper {

    private ConfiguracionMapper() {}

    public static ConfiguracionModel toModel(ConfiguracionEntity e) {
        if (e == null) return null;
        return new ConfiguracionModel(
            e.getId(),
            e.getMaxAutos(),
            e.getMaxMotos(),
            e.getPenalizacionPorMin(),
            e.getMaxTiempoPrestamoMin(),
            e.getMaxEstacionamientosPorApartamento(),
            e.getMaxCarritosPorApartamento(),
            e.getMaxVehiculosPorPropietario(),
            e.getMaxInquilinosPorApartamento()
        );
    }

    public static ConfiguracionEntity toEntity(ConfiguracionModel m) {
        if (m == null) return null;
        ConfiguracionEntity e = new ConfiguracionEntity();
        e.setId(m.getId());
        e.setMaxAutos(m.getMaxAutos());
        e.setMaxMotos(m.getMaxMotos());
        e.setPenalizacionPorMin(m.getPenalizacionPorMin());
        e.setMaxTiempoPrestamoMin(m.getMaxTiempoPrestamoMin());
        e.setMaxEstacionamientosPorApartamento(m.getMaxEstacionamientosPorApartamento());
        e.setMaxCarritosPorApartamento(m.getMaxCarritosPorApartamento());
        e.setMaxVehiculosPorPropietario(m.getMaxVehiculosPorPropietario());
        e.setMaxInquilinosPorApartamento(m.getMaxInquilinosPorApartamento());
        return e;
    }
}
