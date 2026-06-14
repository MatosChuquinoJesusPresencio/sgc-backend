package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;
import org.springframework.stereotype.Component;

@Component
public class TorreMapper {

    public TorreModel aModelo(TorreEntity entidad) {
        return new TorreModel(
            entidad.getId(), entidad.getNombre(), entidad.getIdCondominio());
    }

    public TorreEntity aEntidad(TorreModel modelo) {
        TorreEntity entidad = new TorreEntity();
        entidad.setId(modelo.getId());
        entidad.setNombre(modelo.getNombre());
        if (modelo.getIdCondominio() != null) {
            CondominioEntity referencia = new CondominioEntity();
            referencia.setId(modelo.getIdCondominio());
            entidad.setCondominio(referencia);
        }
        return entidad;
    }
}
