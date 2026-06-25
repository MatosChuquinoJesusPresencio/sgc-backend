package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.ApartamentoEntity;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.PisoEntity;
import java.util.List;

public final class PisoMapper {

    private PisoMapper() {}

    public static PisoModel toModel(PisoEntity e) {
        if (e == null) return null;
        List<ApartamentoModel> apartamentos = e.getApartamentos().stream()
            .map(ApartamentoMapper::toModel)
            .toList();
        return new PisoModel(e.getId(), e.getNumero(), apartamentos);
    }

    public static PisoEntity toEntity(PisoModel m) {
        if (m == null) return null;
        PisoEntity e = new PisoEntity();
        e.setId(m.getId());
        e.setNumero(m.getNumero());
        if (m.getApartamentos() != null) {
            List<ApartamentoEntity> apartamentos = m.getApartamentos().stream()
                .map(ApartamentoMapper::toEntity)
                .toList();
            apartamentos.forEach(a -> a.setPiso(e));
            e.setApartamentos(apartamentos);
        }
        return e;
    }
}
