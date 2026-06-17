package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class ApartamentoMapper {

    public ApartamentoModel aModelo(ApartamentoEntity entidad) {
        return new ApartamentoModel(
            entidad.getId(), entidad.getNumero(), entidad.getDerechoEstacionamiento(),
            entidad.getMetraje(), entidad.getIdPropietario(), entidad.getIdPiso());
    }

    public ApartamentoEntity aEntidad(ApartamentoModel modelo) {
        ApartamentoEntity entidad = new ApartamentoEntity();
        entidad.setId(modelo.getId());
        entidad.setNumero(modelo.getNumero());
        entidad.setDerechoEstacionamiento(modelo.getDerechoEstacionamiento());
        entidad.setMetraje(modelo.getMetraje());
        if (modelo.getIdPropietario() != null) {
            UsuarioEntity referencia = new UsuarioEntity();
            referencia.setId(modelo.getIdPropietario());
            entidad.setPropietario(referencia);
        }
        if (modelo.getIdPiso() != null) {
            PisoEntity referencia = new PisoEntity();
            referencia.setId(modelo.getIdPiso());
            entidad.setPiso(referencia);
        }
        return entidad;
    }
}
