package com.condominios.sgc.infrastructure.persistence.mapper;

import static com.condominios.sgc.domain.util.EntidadUtil.idDe;

import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.infrastructure.persistence.entity.EstacionamientoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.VehiculoEntity;

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
        e.setTipo(model.getTipo());
        return e;
    }

    public static VehiculoModel toModel(VehiculoEntity e) {
        if (e == null) return null;
        return new VehiculoModel(
                e.getId(), 
                e.getMarca(), 
                e.getColor(), 
                e.getModelo(), 
                e.getPlaca(),
                e.getTipo(),
                idDe(e.getPropietario(), UsuarioEntity::getId),
                idDe(e.getInquilino(), InquilinoEntity::getId),
                idDe(e.getEstacionamiento(), EstacionamientoEntity::getId)
        );
    }
}
