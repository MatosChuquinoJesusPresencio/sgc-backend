package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.shared.valueobject.NombreCompleto;
import com.condominios.sgc.domain.shared.valueobject.NumeroDocumento;
import com.condominios.sgc.domain.type.TipoDocumento;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.InquilinoEntity;

public final class InquilinoMapper {

    private InquilinoMapper() {}

    public static InquilinoModel toModel(InquilinoEntity e) {
        if (e == null) return null;
        return new InquilinoModel(
            e.getId(),
            new NombreCompleto(e.getNombres(), e.getApellidos()),
            new NumeroDocumento(TipoDocumento.valueOf(e.getTipoDocumento()), e.getNumeroDocumento()),
            e.getIdApartamento()
        );
    }

    public static InquilinoEntity toEntity(InquilinoModel m) {
        if (m == null) return null;
        InquilinoEntity e = new InquilinoEntity();
        e.setId(m.getId());
        e.setNombres(m.getNombres());
        e.setApellidos(m.getApellidos());
        e.setTipoDocumento(m.getNumeroDocumento().tipo().name());
        e.setNumeroDocumento(m.getNumeroDocumento().numero());
        e.setIdApartamento(m.getIdApartamento());
        return e;
    }
}
