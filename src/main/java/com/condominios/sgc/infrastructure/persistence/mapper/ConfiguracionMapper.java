package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.ConfiguracionEntity;
import org.springframework.stereotype.Component;

@Component
public class ConfiguracionMapper {

    public ConfiguracionModel aModelo(ConfiguracionEntity entidad) {
        return new ConfiguracionModel(
            entidad.getId(), entidad.getMaxAutos(), entidad.getMaxMotos(),
            entidad.getPenalizacionPorMin(), entidad.getMaxTiempoPrestamoMin(),
            entidad.getMaxEstacionamientosPorApartamento(),
            entidad.getMaxCarritosPorApartamento(),
            entidad.getMaxVehiculosPorPropietario(),
            entidad.getMaxInquilinosPorApartamento(),
            entidad.getIdCondominio());
    }

    public ConfiguracionEntity aEntidad(ConfiguracionModel modelo) {
        ConfiguracionEntity entidad = new ConfiguracionEntity();
        entidad.setId(modelo.getId());
        entidad.setMaxAutos(modelo.getMaxAutos());
        entidad.setMaxMotos(modelo.getMaxMotos());
        entidad.setPenalizacionPorMin(modelo.getPenalizacionPorMin());
        entidad.setMaxTiempoPrestamoMin(modelo.getMaxTiempoPrestamoMin());
        entidad.setMaxEstacionamientosPorApartamento(modelo.getMaxEstacionamientosPorApartamento());
        entidad.setMaxCarritosPorApartamento(modelo.getMaxCarritosPorApartamento());
        entidad.setMaxVehiculosPorPropietario(modelo.getMaxVehiculosPorPropietario());
        entidad.setMaxInquilinosPorApartamento(modelo.getMaxInquilinosPorApartamento());
        if (modelo.getIdCondominio() != null) {
            CondominioEntity referencia = new CondominioEntity();
            referencia.setId(modelo.getIdCondominio());
            entidad.setCondominio(referencia);
        }
        return entidad;
    }
}
