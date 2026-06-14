package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.CiudadModel;
import com.condominios.sgc.infrastructure.persistence.entity.CiudadEntity;
import com.condominios.sgc.infrastructure.persistence.entity.PaisEntity;
import org.springframework.stereotype.Component;

@Component
public class CiudadMapper {

    public CiudadModel aModelo(CiudadEntity entidad) {
        return new CiudadModel(
            entidad.getId(), entidad.getNombre(), entidad.getIdPais());
    }

    public CiudadEntity aEntidad(CiudadModel modelo) {
        CiudadEntity entidad = new CiudadEntity();
        entidad.setId(modelo.getId());
        entidad.setNombre(modelo.getNombre());
        if (modelo.getIdPais() != null) {
            PaisEntity referencia = new PaisEntity();
            referencia.setId(modelo.getIdPais());
            entidad.setPais(referencia);
        }
        return entidad;
    }
}
