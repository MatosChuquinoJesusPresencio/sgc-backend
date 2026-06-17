package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.infrastructure.persistence.entity.CiudadEntity;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.PaisEntity;

import org.springframework.stereotype.Component;

@Component
public class CondominioMapper {

    public CondominioModel aModelo(CondominioEntity entidad) {
        return new CondominioModel(
            entidad.getId(), entidad.getNombre(), entidad.getIdPais(),
            entidad.getIdCiudad(), entidad.getDireccion(), entidad.getActivo(), entidad.getFechaCreacion());
    }

    public CondominioEntity aEntidad(CondominioModel modelo) {
        CondominioEntity entidad = new CondominioEntity();
        entidad.setId(modelo.getId());
        entidad.setNombre(modelo.getNombre());
        if (modelo.getIdPais() != null) {
            PaisEntity pais = new PaisEntity();
            pais.setId(modelo.getIdPais());
            entidad.setPais(pais);
        }
        if (modelo.getIdCiudad() != null) {
            CiudadEntity ciudad = new CiudadEntity();
            ciudad.setId(modelo.getIdCiudad());
            entidad.setCiudad(ciudad);
        }
        entidad.setDireccion(modelo.getDireccion());
        entidad.setActivo(modelo.getActivo());
        entidad.setFechaCreacion(modelo.getFechaCreacion());
        return entidad;
    }
}
