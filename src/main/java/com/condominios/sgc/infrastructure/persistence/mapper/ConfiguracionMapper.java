package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.infrastructure.persistence.entity.ConfiguracionEntity;

public class ConfiguracionMapper {

    public ConfiguracionModel aDominio(ConfiguracionEntity entidad) {
        return new ConfiguracionModel(
            entidad.getId(),
            entidad.getMaxAutos(),
            entidad.getMaxMotos(),
            entidad.getPenalizacionPorMin(),
            entidad.getMaxTiempoPrestamoMin(),
            entidad.getMaxEstacionamientosPorApartamento(),
            entidad.getMaxCarritosPorApartamento(),
            entidad.getMaxVehiculosPorPropietario(),
            entidad.getMaxInquilinosPorApartamento()
        );
    }

    public ConfiguracionEntity aEntidad(ConfiguracionModel dominio) {
        ConfiguracionEntity entidad = new ConfiguracionEntity();
        entidad.setId(dominio.getId());
        entidad.setMaxAutos(dominio.getMaxAutos());
        entidad.setMaxMotos(dominio.getMaxMotos());
        entidad.setPenalizacionPorMin(dominio.getPenalizacionPorMin());
        entidad.setMaxTiempoPrestamoMin(dominio.getMaxTiempoPrestamoMin());
        entidad.setMaxEstacionamientosPorApartamento(dominio.getMaxEstacionamientosPorApartamento());
        entidad.setMaxCarritosPorApartamento(dominio.getMaxCarritosPorApartamento());
        entidad.setMaxVehiculosPorPropietario(dominio.getMaxVehiculosPorPropietario());
        entidad.setMaxInquilinosPorApartamento(dominio.getMaxInquilinosPorApartamento());
        return entidad;
    }
}
