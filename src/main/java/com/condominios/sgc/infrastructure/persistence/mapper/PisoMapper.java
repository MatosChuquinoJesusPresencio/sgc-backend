package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;
import org.springframework.stereotype.Component;

@Component
public class PisoMapper {

    public PisoModel aModelo(PisoEntity entidad) {
        return new PisoModel(
            entidad.getId(), entidad.getNumero(), entidad.getIdTorre());
    }

    public PisoEntity aEntidad(PisoModel modelo) {
        PisoEntity entidad = new PisoEntity();
        entidad.setId(modelo.getId());
        entidad.setNumero(modelo.getNumero());
        if (modelo.getIdTorre() != null) {
            TorreEntity referencia = new TorreEntity();
            referencia.setId(modelo.getIdTorre());
            entidad.setTorre(referencia);
        }
        return entidad;
    }
}
