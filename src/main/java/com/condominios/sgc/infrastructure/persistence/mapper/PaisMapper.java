package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.PaisModel;
import com.condominios.sgc.infrastructure.persistence.entity.MonedaEntity;
import com.condominios.sgc.infrastructure.persistence.entity.PaisEntity;
import org.springframework.stereotype.Component;

@Component
public class PaisMapper {

    public PaisModel aModelo(PaisEntity entidad) {
        return new PaisModel(
            entidad.getId(), entidad.getNombre(), entidad.getCodigoIso(),
            entidad.getIdMoneda());
    }

    public PaisEntity aEntidad(PaisModel modelo) {
        PaisEntity entidad = new PaisEntity();
        entidad.setId(modelo.getId());
        entidad.setNombre(modelo.getNombre());
        entidad.setCodigoIso(modelo.getCodigoIso());
        if (modelo.getIdMoneda() != null) {
            MonedaEntity referencia = new MonedaEntity();
            referencia.setId(modelo.getIdMoneda());
            entidad.setMoneda(referencia);
        }
        return entidad;
    }
}
