package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.shared.value_objects.NombreCompleto;
import com.condominios.sgc.domain.shared.value_objects.NumeroDocumento;
import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;

public class InquilinoMapper {

    public InquilinoModel aDominio(InquilinoEntity entidad) {
        return new InquilinoModel(
            entidad.getId(),
            new NombreCompleto(entidad.getNombres(), entidad.getApellidos()),
            new NumeroDocumento(entidad.getTipoDocumento(), entidad.getNumeroDocumento()),
            entidad.getIdApartamento()
        );
    }

    public InquilinoEntity aEntidad(InquilinoModel dominio) {
        InquilinoEntity entidad = new InquilinoEntity();
        entidad.setId(dominio.getId());
        entidad.setNombres(dominio.getNombres());
        entidad.setApellidos(dominio.getApellidos());
        entidad.setTipoDocumento(dominio.getNumeroDocumento().tipo());
        entidad.setNumeroDocumento(dominio.getNumeroDocumento().numero());
        entidad.setIdApartamento(dominio.getIdApartamento());
        return entidad;
    }
}
