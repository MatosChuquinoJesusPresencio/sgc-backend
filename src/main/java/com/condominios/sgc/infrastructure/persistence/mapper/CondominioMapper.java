package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import org.springframework.stereotype.Component;

@Component
public class CondominioMapper {

    public CondominioModel aModelo(CondominioEntity entidad) {
        return new CondominioModel(
            entidad.getId(), entidad.getNombre(), entidad.getIdPais(),
            entidad.getIdCiudad(), entidad.getDireccion(), entidad.getFechaCreacion());
    }

    public CondominioEntity aEntidad(CondominioModel modelo) {
        CondominioEntity entidad = new CondominioEntity();
        entidad.setId(modelo.getId());
        entidad.setNombre(modelo.getNombre());
        entidad.setIdPais(modelo.getIdPais());
        entidad.setIdCiudad(modelo.getIdCiudad());
        entidad.setDireccion(modelo.getDireccion());
        entidad.setFechaCreacion(modelo.getFechaCreacion());
        return entidad;
    }
}
