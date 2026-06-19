package com.condominios.sgc.infrastructure.persistence.mapper;

import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.shared.value_objects.PlacaVehiculo;
import com.condominios.sgc.infrastructure.persistence.entity.VehiculoEntity;

public class VehiculoMapper {

    public VehiculoModel aDominio(VehiculoEntity entidad) {
        return new VehiculoModel(
            entidad.getId(),
            entidad.getMarca(),
            entidad.getColor(),
            entidad.getModelo(),
            new PlacaVehiculo(entidad.getPlaca()),
            entidad.getTipo(),
            entidad.getIdPropietario(),
            entidad.getIdInquilino(),
            entidad.getIdEstacionamiento(),
            entidad.getIdCondominio()
        );
    }

    public VehiculoEntity aEntidad(VehiculoModel dominio) {
        VehiculoEntity entidad = new VehiculoEntity();
        entidad.setId(dominio.getId());
        entidad.setMarca(dominio.getMarca());
        entidad.setColor(dominio.getColor());
        entidad.setModelo(dominio.getModelo());
        entidad.setPlaca(dominio.getPlaca().valor());
        entidad.setTipo(dominio.getTipo());
        entidad.setIdCondominio(dominio.getIdCondominio());
        entidad.setIdPropietario(dominio.getIdPropietario());
        entidad.setIdInquilino(dominio.getIdInquilino());
        entidad.setIdEstacionamiento(dominio.getIdEstacionamiento());
        return entidad;
    }
}
