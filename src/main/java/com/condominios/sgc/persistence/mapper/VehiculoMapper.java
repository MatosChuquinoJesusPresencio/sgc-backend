package com.condominios.sgc.persistence.mapper;

import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.persistence.entity.VehiculoEntity;

public final class VehiculoMapper {

    private VehiculoMapper() {}

    public static VehiculoEntity toEntity(VehiculoModel model) {
        if (model == null) return null;
        VehiculoEntity e = new VehiculoEntity();
        e.setId(model.getId());
        e.setMarca(model.getMarca());
        e.setColor(model.getColor());
        e.setModelo(model.getModelo());
        e.setPlaca(model.getPlaca());
        return e;
    }

    public static VehiculoModel toModel(VehiculoEntity e) {
        if (e == null) return null;
        VehiculoModel m = new VehiculoModel(
                e.getId(), e.getMarca(), e.getColor(), e.getModelo(), e.getPlaca());
        if (e.getPropietarioUsuario() != null) {
            m.asignarAUsuario(e.getPropietarioUsuario().getId());
        }
        if (e.getPropietarioInquilino() != null) {
            m.asignarAInquilino(e.getPropietarioInquilino().getId());
        }
        return m;
    }
}
