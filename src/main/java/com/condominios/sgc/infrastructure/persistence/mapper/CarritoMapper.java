package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.infrastructure.persistence.entity.CarritoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import org.springframework.stereotype.Component;

@Component
public class CarritoMapper {

    public CarritoModel aModelo(CarritoEntity entidad) {
        return new CarritoModel(
            entidad.getId(), entidad.getCodigo(), entidad.getEstado(),
            entidad.getIdCondominio());
    }

    public CarritoEntity aEntidad(CarritoModel modelo) {
        CarritoEntity entidad = new CarritoEntity();
        entidad.setId(modelo.getId());
        entidad.setCodigo(modelo.getCodigo());
        entidad.setEstado(modelo.getEstado());
        if (modelo.getIdCondominio() != null) {
            CondominioEntity referencia = new CondominioEntity();
            referencia.setId(modelo.getIdCondominio());
            entidad.setCondominio(referencia);
        }
        return entidad;
    }
}
