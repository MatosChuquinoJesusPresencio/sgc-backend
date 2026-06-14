package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;
import org.springframework.stereotype.Component;

@Component
public class InquilinoMapper {

    public InquilinoModel aModelo(InquilinoEntity entidad) {
        return new InquilinoModel(
            entidad.getId(), entidad.getNombres(), entidad.getApellidos(),
            entidad.getTipoDocumento(), entidad.getNumeroDocumento(),
            entidad.getIdApartamento());
    }

    public InquilinoEntity aEntidad(InquilinoModel modelo) {
        InquilinoEntity entidad = new InquilinoEntity();
        entidad.setId(modelo.getId());
        entidad.setNombres(modelo.getNombres());
        entidad.setApellidos(modelo.getApellidos());
        entidad.setTipoDocumento(modelo.getTipoDocumento());
        entidad.setNumeroDocumento(modelo.getNumeroDocumento());
        if (modelo.getIdApartamento() != null) {
            ApartamentoEntity referencia = new ApartamentoEntity();
            referencia.setId(modelo.getIdApartamento());
            entidad.setApartamento(referencia);
        }
        return entidad;
    }
}
