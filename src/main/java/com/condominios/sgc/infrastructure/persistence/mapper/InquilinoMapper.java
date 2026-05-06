package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;

public final class InquilinoMapper {

    private InquilinoMapper() {}

    public static InquilinoEntity toEntity(InquilinoModel model) {
        if (model == null) return null;
        InquilinoEntity e = new InquilinoEntity();
        e.setId(model.getId());
        e.setNombres(model.getNombres());
        e.setApellidos(model.getApellidos());
        e.setDni(model.getDni());
        return e;
    }

    public static InquilinoModel toModel(InquilinoEntity e) {
        if (e == null) return null;
        InquilinoModel m = new InquilinoModel(
                e.getId(), e.getNombres(), e.getApellidos(), e.getDni(),
                e.getApartamento() != null ? e.getApartamento().getId() : null);
        if (e.getVehiculo() != null) {
            m.asignarVehiculo(e.getVehiculo().getId());
        }
        return m;
    }
}
