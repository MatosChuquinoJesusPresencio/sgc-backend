package com.condominios.sgc.infrastructure.adapter.out.persistence.mapper;

import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.shared.valueobject.PlacaVehiculo;
import com.condominios.sgc.domain.type.TipoVehiculo;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.VehiculoEntity;

public final class VehiculoMapper {

    private VehiculoMapper() {}

    public static VehiculoModel toModel(VehiculoEntity e) {
        if (e == null) return null;
        return new VehiculoModel(
            e.getId(),
            e.getMarca(),
            e.getColor(),
            e.getModelo(),
            new PlacaVehiculo(e.getPlaca()),
            TipoVehiculo.valueOf(e.getTipo()),
            e.getIdPropietario(),
            e.getIdInquilino(),
            e.getIdEstacionamiento(),
            e.getIdCondominio()
        );
    }

    public static VehiculoEntity toEntity(VehiculoModel m) {
        if (m == null) return null;
        VehiculoEntity e = new VehiculoEntity();
        e.setId(m.getId());
        e.setMarca(m.getMarca());
        e.setColor(m.getColor());
        e.setModelo(m.getModelo());
        e.setPlaca(m.getPlaca().valor());
        e.setTipo(m.getTipo().name());
        e.setIdPropietario(m.getIdPropietario());
        e.setIdInquilino(m.getIdInquilino());
        e.setIdEstacionamiento(m.getIdEstacionamiento());
        e.setIdCondominio(m.getIdCondominio());
        return e;
    }
}
